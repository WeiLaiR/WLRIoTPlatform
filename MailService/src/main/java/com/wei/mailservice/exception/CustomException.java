package com.wei.mailservice.exception;

import lombok.Getter;

/**
 * 自定义异常
 * 输入两个String
 * state是状态
 * message是消息
 */
@Getter
public class CustomException extends RuntimeException {
    private String status;
    private String message;

    public CustomException(String code, String message) {
        this.status = code;
        this.message = message;
    }

}
