package com.tfjybj.typing.utils.exception;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.StringUtils;

public class ExceptionUtil {
    private final static String CODE_EXPRESS = ";#code=(.*?)";
    private final static String CODE_BEGIN = ";#code=";
    protected static Logger logger = LoggerFactory.getLogger(ExceptionUtil.class);

    public static String parseMessage(String message) {
        try {
            if (message.contains(CODE_BEGIN)) {
                return message.split(CODE_BEGIN)[0];
            }
            return message;
        } catch (Exception ex) {
            return message;
        }
    }

    public static String parseCode(String message) {
        try {
            if (message.contains(CODE_BEGIN)) {
                return message.split(CODE_BEGIN)[1];
            }
            return "";
        } catch (Exception ex) {
            return "";
        }
//        Pattern pattern = Pattern.compile(CODE_EXPRESS);//匹配的模式
//        Matcher matcher = pattern.matcher(message);
//        while (matcher.find()) {
//            logger.info(matcher.group());
//            //return matcher.group(1);
//        }
//        return "";
    }

    public static String joinMsgAndCode(String message, String code) {
        if (StringUtils.isEmpty(code)) {
            return message;
        }

        return message + CODE_BEGIN + code;
    }

    public static void main(String[] args) {
        String temp = "异常测试";//"异常测试;#code=003";

        String message = parseMessage(temp);
        logger.info(message);

        String code = parseCode(temp);
        logger.info(code);

        String msgCode = joinMsgAndCode(message, code);
        logger.info(msgCode);

        message = parseMessage(msgCode);
        logger.info(message);

        code = parseCode(msgCode);
        logger.info(code);
    }
}
