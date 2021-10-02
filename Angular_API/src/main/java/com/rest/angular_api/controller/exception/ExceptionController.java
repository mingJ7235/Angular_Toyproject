package com.rest.angular_api.controller.exception;

import com.rest.angular_api.advice.exception.CAuthenticationEntryPointException;
import com.rest.angular_api.model.response.CommonResult;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.nio.file.AccessDeniedException;

@RequiredArgsConstructor
@RestController
@RequestMapping (value = "/exception")
public class ExceptionController {

    @GetMapping (value = "/entrypoint")
    public CommonResult entrypointException () {
        throw new CAuthenticationEntryPointException();
    }

    @GetMapping (value = "/accessdenied")
    public CommonResult accessdeniedException () throws AccessDeniedException {
        throw new AccessDeniedException("");
    }
}
