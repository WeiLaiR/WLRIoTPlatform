package com.wei.iotplatformuserservice.exception;

import lombok.Getter;

/**
 * 自定义异常
 * 输入两个String
 * state是状态
 * message是消息
 */
@Getter
public class CustomException extends RuntimeException {
    private Object status;
    private String message;

    public CustomException(Object code, String message) {
        this.status = code;
        this.message = message;
    }

}
