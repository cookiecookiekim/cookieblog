package com.cookieblog.controller;

import com.cookieblog.exception.CookieException;
import com.cookieblog.exception.InvalidRequest;
import com.cookieblog.exception.PostNotFound;
import com.cookieblog.response.ErrorResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.*;
import com.cookieblog.response.*;

import java.util.HashMap;
import java.util.Map;

@Slf4j
@ControllerAdvice //스프링에서 전역적으로 예외를 처리하거나, 여러 컨트롤러에서 공통적으로 사용하는 로직(예: 데이터 바인딩, 모델 속성 추가 등)을 정의할 때 사용
public class ExceptionController {

//    @ResponseStatus(HttpStatus.BAD_REQUEST) // 해당 메서드가 정상적으로 실행되었을 때, 또는 예외 처리 메서드가 실행되었을 때 반환되는 HTTP 상태 코드를 지정할 때 사용
//    @ExceptionHandler(Exception.class) // 특정 예외 발생 시 사용
//                                // 컨트롤러 계층(즉, @Controller 또는 @RestController)에서 예외가 발생했을 때, 해당 예외를 잡아서 처리하고 싶을 때 사용
//    public void exceptionHandler(Exception e){
//        log.info("exceptionHandler error",e);
//
//    }

//    @ResponseStatus(HttpStatus.BAD_REQUEST)
//    @ExceptionHandler(MethodArgumentNotValidException.class)
//    @ResponseBody // 요거 넣어야 json , body 넘어감
//    public Map<String , String> invalidRequestHandler(MethodArgumentNotValidException e){
//        FieldError fieldError = e.getFieldError();
//        String field = fieldError.getField();
//        String message = fieldError.getDefaultMessage();
//
//        // json으로 넘기기
//        Map<String , String> response = new HashMap<>();
//        response.put(field,message);
//        return response;
//    }
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(MethodArgumentNotValidException.class)
    @ResponseBody
    public ErrorResponse invalidRequestHandler(MethodArgumentNotValidException e){
        ErrorResponse response = ErrorResponse.builder()
                .code("400")
                .message("잘못된 요청입니다.")
                .build();

        for (FieldError fieldError : e.getFieldErrors()){
            response.addValidation(fieldError.getField(), fieldError.getDefaultMessage());
        }
        return response;
    }

    @ResponseBody
//    @ResponseStatus(HttpStatus.NOT_FOUND) 이렇게 ResponseStatus를 고정하면 동적으로 변경 불가능
    @ExceptionHandler(CookieException.class) // 예외 발생하면 ExceptionHandler가 자동으로 호출
    public ResponseEntity<ErrorResponse> cookieException(CookieException e){
        int statusCode = e.getStatusCode(); // 동적으로 처리할 수 있게 설정

        ErrorResponse body = ErrorResponse.builder()
                .code(String.valueOf(statusCode))
                .message(e.getMessage())
                .validation(e.getValidation())
                .build();

        // 응답 json validation -> title : 제목에 바보를 포함할 수 없습니다.

        ResponseEntity<ErrorResponse> response = ResponseEntity.status(statusCode)
                .body(body);

        return response;
    }

    // 이렇게 많아질 건데 효율적으로 관리하기위해 최상위 예외 클래스 생성 CookieException
//    @ResponseBody
//    @ResponseStatus(HttpStatus.NOT_FOUND)
//    @ExceptionHandler(InvalidRequest.class)
//    public ErrorResponse invalidRequest(InvalidRequest e){
//        ErrorResponse response = ErrorResponse.builder()
//                .code("404")
//                .message(e.getMessage())
//                .build();
//        return response;
//    }

}
