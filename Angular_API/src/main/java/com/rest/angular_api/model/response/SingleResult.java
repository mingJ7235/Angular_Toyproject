package com.rest.angular_api.model.response;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
/**
 * Generic을 사용하여 어떤 형태의 값이 들어가도 될 수 있도록 구현
 * CommonResult를 상속 받으므로, api요청 결과도 같이 출력됨
 */
public class SingleResult<T> extends CommonResult{
    private T data;

}
