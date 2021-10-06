package com.rest.angular_api.advice;

import com.rest.angular_api.advice.exception.*;
import com.rest.angular_api.model.response.CommonResult;
import com.rest.angular_api.service.ResponseService;
import lombok.RequiredArgsConstructor;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import javax.servlet.http.HttpServletRequest;
import java.nio.file.AccessDeniedException;

@RequiredArgsConstructor
/**
 * ControllerAdvice
 *      - @ControllerAdvice
 *      - @RestControllerAdvice : Exception 발생시 json형태로 결과 반환
 */
@RestControllerAdvice
public class ExceptionAdvice {
    private final ResponseService responseService;

    private  final MessageSource messageSource;

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
        return responseService.getFailResult(Integer.valueOf(getMessage("unKnown.code")), getMessage("unKnown.msg"));
    }

    @ExceptionHandler (CUserNotFoundException.class)
    @ResponseStatus (HttpStatus.INTERNAL_SERVER_ERROR)
    protected CommonResult userNotFoundException (HttpServletRequest request, CUserNotFoundException e) {
        return responseService.getFailResult(Integer.valueOf(getMessage("userNotFound.code")), getMessage("userNotFound.msg"));
    }

    @ExceptionHandler (CEmailSignInFailedException.class)
    @ResponseStatus (HttpStatus.INTERNAL_SERVER_ERROR)
    protected CommonResult emailSignInFailedException (HttpServletRequest request, CEmailSignInFailedException e) {
        return responseService.getFailResult(Integer.valueOf(getMessage("emailSignInFailed.code")), getMessage("emailSignInFailed.msg"));

    }
    @ExceptionHandler (CAuthenticationEntryPointException.class)
    @ResponseStatus (HttpStatus.INTERNAL_SERVER_ERROR)
    protected CommonResult authenticationEntryPointException (HttpServletRequest request, CAuthenticationEntryPointException e) {
        return responseService.getFailResult(Integer.valueOf(getMessage("entryPointException.code")), getMessage("entryPointException.msg"));
    }

    @ExceptionHandler (AccessDeniedException.class)
    @ResponseStatus (HttpStatus.INTERNAL_SERVER_ERROR)
    protected CommonResult accessDeniedException (HttpServletRequest request, AccessDeniedException e) {
        return responseService.getFailResult(Integer.valueOf(getMessage("accessDenied.code")), getMessage("accessDenied.msg"));
    }

    @ExceptionHandler (CCommunicationException.class)
    @ResponseStatus (HttpStatus.INTERNAL_SERVER_ERROR)
    protected CommonResult accessDeniedException (HttpServletRequest request, CCommunicationException e) {
        return responseService.getFailResult(Integer.valueOf(getMessage("communicationError.code")), getMessage("communicationError.msg"));
    }

    @ExceptionHandler (CUserExistException.class)
    @ResponseStatus (HttpStatus.INTERNAL_SERVER_ERROR)
    protected CommonResult userExistException (HttpServletRequest request, CUserExistException e) {
        return responseService.getFailResult(Integer.valueOf(getMessage("existingUser.code")), getMessage("existingUser.msg"));

    }

    @ExceptionHandler (CNotOwnerException.class)
    @ResponseStatus (HttpStatus.NON_AUTHORITATIVE_INFORMATION)
    public CommonResult notOwnerException (HttpServletRequest request, CNotOwnerException e) {
        return responseService.getFailResult(Integer.valueOf(getMessage("notOwner.code")), getMessage("notOwner.msg"));
    }

    @ExceptionHandler (CResourceNotExistException.class)
    @ResponseStatus (HttpStatus.NOT_FOUND)
    public CommonResult resourceNotExistException (HttpServletRequest request, CResourceNotExistException e) {
        return responseService.getFailResult(Integer.valueOf(getMessage("resourceNotExist.code")), getMessage("resourceNotExist.msg"));
    }


    private String getMessage (String code) {
        return getMessage(code, null);
    }

    private String getMessage (String code, Object[] args) {
        return messageSource.getMessage(code, args, LocaleContextHolder.getLocale());
    }
}
