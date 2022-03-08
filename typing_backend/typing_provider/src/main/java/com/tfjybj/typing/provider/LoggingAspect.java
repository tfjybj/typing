package com.tfjybj.typing.provider;

import com.alibaba.fastjson.JSON;
import com.baomidou.mybatisplus.core.toolkit.ArrayUtils;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import java.lang.reflect.Method;
import java.lang.reflect.Parameter;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * @author 杨燕梅
 * @description 配置切面打印日志
 * @date 2020/10/27 20:29
 */
@Aspect
@Component
public class LoggingAspect {
    private final Logger logger=LoggerFactory.getLogger(this.getClass());

    /**
     * 记录业务请求的时间
     */
    private long requsetstartTime;
    private String startTime;

    // 定义切点PointCut
    @Pointcut("execution(public * com.tfjybj.typing.provider.*controller..*.*(..))")
    private void controllerMethod(){
    }

    @Around("controllerMethod()")
    public Object printRequestAndResponds(ProceedingJoinPoint joinPoint)throws Throwable{
        //http请求
        HttpServletRequest httpServletRequest = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest();
        //ip地址
        String ip = getIpAddress(httpServletRequest);
        //请求时间
        SimpleDateFormat dateFormat = new SimpleDateFormat ("yyyy-MM-dd HH:mm:ss");
        requsetstartTime = System.currentTimeMillis();
        startTime = dateFormat.format (new Date(requsetstartTime));
        //方法名
        Method method = ((MethodSignature) joinPoint.getSignature()).getMethod();
        //uri
        String uri = httpServletRequest.getRequestURI();
        ////请求参数
        String requestParam =getRequestParam(joinPoint);
        logger.info("***************************************************");
        logger.info ("==> 拦截到请求\n"
                + "==> 请求者IP：" + ip + "\n"
                + "==> 请求时间：" + startTime + "\n"
                + "==> 请求接口：" + httpServletRequest.getMethod() + " " + httpServletRequest.getRequestURL() + "\n"
                + "==> 请求方法：" + method.getName() + "\n"
                + "==> 请求参数内容：" + requestParam);

        // result的值就是被拦截方法的返回值
        Object result = joinPoint.proceed();
        long handleTime = System.currentTimeMillis() - requsetstartTime;
        String d = String.valueOf(handleTime);
        logger.info("***************************************************");
        logger.info ( "<== 响应请求\n"
                + "<== 请求时间：" + startTime + "\n"
                + "<== 请求耗时：" + Double.parseDouble(d)/1000 + "s\n"
                + "<== 响应内容：" + requestParam + JSON.toJSONString(result) );
        logger.info("***************************************************");
        return result;
    }

    /**
     * 获取请求IP地址
     *
     * @param request
     * @return java.lang.String
     * @author 杨燕梅
     * @since 2020/10/28 15:12
     */
    private String getIpAddress(HttpServletRequest request){
        final String UNKNOWN = "unknown";
        String ip = request.getHeader("x-forwarded-for");
        if (ip == null || ip.length() == 0 || UNKNOWN.equalsIgnoreCase(ip)) {
            ip = request.getHeader("Proxy-Client-IP");
        }
        if (ip == null || ip.length() == 0 || UNKNOWN.equalsIgnoreCase(ip)) {
            ip = request.getHeader("WL-Proxy-Client-IP");
        }
        if (ip == null || ip.length() == 0 || UNKNOWN.equalsIgnoreCase(ip)) {
            ip = request.getHeader("HTTP_CLIENT_IP");
        }
        if (ip == null || ip.length() == 0 || UNKNOWN.equalsIgnoreCase(ip)) {
            ip = request.getHeader("HTTP_X_FORWARDED_FOR");
        }
        if (ip == null || ip.length() == 0 || UNKNOWN.equalsIgnoreCase(ip)) {
            ip = request.getRemoteAddr();
        }
        return ip;
    }

    /**
     * 获取请求参数
     *
     * @param point
     * @return java.lang.String
     * @author 杨燕梅
     * @since 2020/10/27 20:52
     */
    private String getRequestParam(ProceedingJoinPoint point){
        Object[] methodArgs=point.getArgs();
        Parameter[] parameters=((MethodSignature)point.getSignature()).getMethod().getParameters();
        String requestParam;
        try {
            requestParam =logParam(parameters,methodArgs);
        }catch (Exception e){
            requestParam="获取参数失败";
        }
        return requestParam;
    }

    /**
     * 拼接参数
     *
     * @param paramsArgsName
     * @param paramsArgsValue
     * @return java.lang.String
     * @author 杨燕梅
     * @since 2020/10/28 15:12
     */
    private String logParam(Parameter[] paramsArgsName,Object[] paramsArgsValue){
        if (ArrayUtils.isEmpty(paramsArgsName ) || ArrayUtils.isEmpty(paramsArgsValue)) {
            return "无参数";
        }
        StringBuffer buffer=new StringBuffer();
        for (int i=0;i<paramsArgsValue.length;i++){
            //参数名
            String name=paramsArgsName[i].getName();
            //参数值
            Object value=paramsArgsValue[i];
            buffer.append(name+"=");
            if (value instanceof String){
                buffer.append(value+",");
            }else{
                buffer.append(JSON.toJSONString(value)+",");
            }
        }
        return buffer.toString();
        }
    }
