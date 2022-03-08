package com.tfjybj.typing.utils.exception;

import java.text.MessageFormat;

/**
 * ApplicationException异常, 继承自Exception.
 */
@SuppressWarnings("serial")
public class ApplicationException extends BaseException {

    private String messageid;
    private String message;
    private String code;
    private Throwable cause;

    /**
     * 异常构造函数
     */
    public ApplicationException(String message) {
        super(message);
    }

    public ApplicationException(String message, Throwable cause) {
        super(message, cause);
    }

    public ApplicationException(String message, String code) {
        super(message, code);
    }

    public ApplicationException(String message, String code, Throwable cause) {
        super(message, code, cause);
    }

    /**
     * 异常构造函数
     *
     * @param messageid 消息ID
     * @param params    消息参数
     */
    public ApplicationException(String messageid, String[] params) {
        super(MessageFormat.format("tal_pac", params));
        this.messageid = messageid;
    }

    /**
     * 异常构造函数
     *
     * @param messageid 消息ID
     * @param params    消息参数
     * @param cause     异常内容
     */
    public ApplicationException(String messageid, String[] params, Throwable cause) {
        super(MessageFormat.format("tal_pac", params), cause);
        this.messageid = messageid;
        this.cause = cause;
    }
}
