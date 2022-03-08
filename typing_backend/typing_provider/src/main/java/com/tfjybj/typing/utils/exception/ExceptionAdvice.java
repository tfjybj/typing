package com.tfjybj.typing.utils.exception;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

import java.io.PrintWriter;
import java.io.StringWriter;

@ControllerAdvice
public class ExceptionAdvice {

    protected static Logger logger = LoggerFactory.getLogger(ExceptionUtil.class);

    @ExceptionHandler({Exception.class})
    @ResponseBody
    public ResultUtil handleApplicationException(Exception e) {
        ResultUtil resultUtil;
        if (e instanceof ApplicationException) {
            ApplicationException girlException = (ApplicationException) e;
            resultUtil = new ResultUtil(girlException.getCode(), girlException.getMessage());
            if (StringUtils.isEmpty(girlException.getCode())) {
                resultUtil.setCode("500");
            }
            return resultUtil;
        }
        logger.info(getStackTrace(e));
        return new ResultUtil("500", "未知异常,请联系管理员！");
    }

    public static String getStackTrace(Throwable throwable)
    {
        StringWriter sw = new StringWriter();
        PrintWriter pw = new PrintWriter(sw);

        try
        {
            throwable.printStackTrace(pw);
            return sw.toString();
        } finally
        {
            pw.close();
        }
    }
}

