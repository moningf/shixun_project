package com.demo.exception;

/**
 * 自定义业务异常 —— Service 层抛，全局异常处理器统一捕获并转为 ApiResponse
 */
public class BusinessException extends RuntimeException {

    private final int code;

    public BusinessException(int code, String message) {
        super(message);
        this.code = code;
    }

    public BusinessException(String message) {
        this(400, message);
    }

    public int getCode() {
        return code;
    }
}
