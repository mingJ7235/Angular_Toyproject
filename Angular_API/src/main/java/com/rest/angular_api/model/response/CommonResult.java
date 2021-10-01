package com.rest.angular_api.model.response;

import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
/**
 * API의 처리상태 및 메시지를 내려주는 데이터로 구성됨.
 * success는 api의 성공, 실패 여부
 * code, msg 는 해당 상황에서의 응답 코드와 메세지를 나타낸다.
 */
public class CommonResult {
    @ApiModelProperty(value = "응답 성공여부 : true / false")
    private boolean success;

    @ApiModelProperty (value = "응답 코드 번호 : >=0 정상, < 0 비정상")
    private int code;

    @ApiModelProperty (value = "응답메세지")
    private String msg;
}
