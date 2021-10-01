package com.rest.angular_api.service;

import com.rest.angular_api.model.response.CommonResult;
import com.rest.angular_api.model.response.ListResult;
import com.rest.angular_api.model.response.SingleResult;
import lombok.Getter;
import org.springframework.stereotype.Service;

import java.util.List;

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
    //단일건 결과 처리 메소드
    public <T> SingleResult<T> getSingleResult (T data) {
        SingleResult<T> result = new SingleResult<>();
        result.setData(data);
        setSuccessResult(result);
        return result;
    }

    //다중건 결과 처리 메소드
   public <T>ListResult<T> getListResult (List<T> list) {
        ListResult<T> result = new ListResult<>();
        result.setList(list);
        setSuccessResult(result);
        return result;
   }

   //성공 결과 처리 메소드
   public CommonResult getSuccessResult () {
        CommonResult result = new CommonResult();
        setSuccessResult(result);
        return result;
   }

    //실패 결과 처리 메소드
   public CommonResult getFailResult () {
        CommonResult result = new CommonResult();
        result.setSuccess(false);
        result.setCode(CommonResponse.FAIL.getCode());
        result.setMsg(CommonResponse.FAIL.getMsg());
        return result;
   }


    private void setSuccessResult (CommonResult result) {
        result.setSuccess(true);
        result.setCode(CommonResponse.SUCCESS.getCode());
        result.setMsg(CommonResponse.SUCCESS.getMsg());
    }




}
