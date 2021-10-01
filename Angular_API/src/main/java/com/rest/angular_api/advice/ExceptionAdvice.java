package com.rest.angular_api.advice;

import com.rest.angular_api.model.response.CommonResult;
import com.rest.angular_api.service.ResponseService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import javax.servlet.http.HttpServletRequest;

@RequiredArgsConstructor
/**
 * ControllerAdvice
 *      - @ControllerAdvice
 *      - @RestControllerAdvice : Exception 발생시 json형태로 결과 반환
 */
@RestControllerAdvice
public class ExceptionAdvice {
    private final ResponseService responseService;

    /**
     * ExceptionHandler
     * - Exception이 발생시, 해당 Handler로 처리 하겠다는 annotation.
     * - 인자로 괄호안에 어떤 Exception이 발생할 때 handler적용할 것인지 Exception class를 넣는다.
     */
    @ExceptionHandler (Exception.class)
    /**
     * 해당 Exception이 발생하면 어떤 HttpStatus code로 반환할것인지 설정.
     * Internal_server_error의 code는 500 이므로, 해당 Exception이 발생하면 code가 500으로 내려간다.
     * 성공시에는 200이다.
     */
    @ResponseStatus (HttpStatus.INTERNAL_SERVER_ERROR)
    protected CommonResult defaultException (HttpServletRequest request, Exception e){
        return responseService.getFailResult();
    }
}
