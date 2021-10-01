package com.rest.angular_api.service;

import lombok.Getter;
import org.springframework.stereotype.Service;

@Service
public class ResponseService {
    //enum으로 api 요청 결과에 대한 code, msg를 정의
    @Getter
    public enum CommonResponse {
        SUCCESS(0, "성공"),
        FAIL (-1, "실패");

        int code;
        String msg;

        CommonResponse(int code, String msg) {
            this.code = code;
            this.msg = msg;
        }
    }




}
