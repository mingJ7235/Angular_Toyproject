package com.rest.angular_api.model.social;

import lombok.Getter;
import lombok.Setter;

/**
 * Kakao token api 연동시 맴핑을 위한 dto 모델
 */
@Getter
@Setter
public class RetKakaoAuth {
    private String access_token;
    private String token_type;
    private String refresh_token;
    private long expires_in;
    private String scope;
}
