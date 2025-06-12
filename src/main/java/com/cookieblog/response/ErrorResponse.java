package com.cookieblog.response;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Builder;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.util.HashMap;
import java.util.Map;

// 응답 전용 클래스
@Getter
//@JsonInclude(value = JsonInclude.Include.NON_EMPTY)// json으로 보낼 때 validation이 존재하지 않다면 {} 보기 싫으니까 공백의 validation을 없애줌
public class ErrorResponse {

    private final String code;
    private final String message;
    private final Map<String, String> validation;

    @Builder
    public ErrorResponse(String code, String message, Map<String ,String> validation) {
        this.code = code;
        this.message = message;
        this.validation = validation;
    }

    public void addValidation(String fieldName , String errorMessage) {
        this.validation.put(fieldName,errorMessage);
    }

}
