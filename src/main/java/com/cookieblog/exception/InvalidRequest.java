package com.cookieblog.exception;

import lombok.Getter;

// 정책상 400으로 내려줘야 함.
@Getter
public class InvalidRequest extends CookieException {

    private static final String MESSAGE = "잘못된 요청입니다.";


    public InvalidRequest() {
        super(MESSAGE);
    }

    public InvalidRequest(String fieldName , String message){
        super(MESSAGE);
        addValidation(fieldName, message);
    }

    @Override
    public int getStatusCode(){
        return 400;
    }
}
