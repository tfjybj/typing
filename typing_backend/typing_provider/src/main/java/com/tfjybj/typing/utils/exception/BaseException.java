package com.tfjybj.typing.utils.exception;


import org.springframework.util.StringUtils;

/**
 * ApplicationException异常, 继承自Exception.
 */
@SuppressWarnings("serial")
public class BaseException extends RuntimeException {


    protected String code = "";

    /**
     * 异常构造函数
     */
    public BaseException(String message) {
        super(message);
    }

    public BaseException(String message, Throwable cause) {
        super(message, cause);
    }

    public BaseException(String message, String code) {
        super(ExceptionUtil.joinMsgAndCode(message, code));
        this.code = code;
    }

    public BaseException(String message, String code, Throwable cause) {
        super(ExceptionUtil.joinMsgAndCode(message, code), cause);
        this.code = code;
    }

    public final String getCode() {
        if (StringUtils.isEmpty(code)) {
            return ExceptionUtil.parseCode(super.getMessage());
        }
        return this.code;
    }
}
