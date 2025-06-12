package com.cookieblog.exception;

import lombok.Getter;

import java.util.HashMap;
import java.util.Map;

// 많아질 예외처리 해결을 위한 최상위 클래스
@Getter
public abstract class CookieException extends RuntimeException{

    private final Map<String ,String> validation = new HashMap<>();

    public CookieException(String message) {
        super(message);
    }

    public CookieException(String message, Throwable cause) {
        super(message, cause);
    }

    public abstract int getStatusCode(); // 반드시 구현되도록 설정 // 반드시 자식 클래스에서 구현하도록 강제

    public void addValidation(String fieldName, String message){
        validation.put(fieldName, message);
    }
}
