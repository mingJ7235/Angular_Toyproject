package com.rest.angular_api.controller.v1;

import com.rest.angular_api.advice.exception.CUserExistException;
import com.rest.angular_api.advice.exception.CUserNotFoundException;
import com.rest.angular_api.config.security.JwtTokenProvider;
import com.rest.angular_api.entity.User;
import com.rest.angular_api.advice.exception.CEmailSignInFailedException;
import com.rest.angular_api.model.response.CommonResult;
import com.rest.angular_api.model.response.SingleResult;
import com.rest.angular_api.model.social.KakaoProfile;
import com.rest.angular_api.repository.UserJpaRepo;
import com.rest.angular_api.service.ResponseService;
import com.rest.angular_api.service.user.KakaoService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.util.Collections;
import java.util.Optional;

@Api(tags = {"1. Sign"})
@RequiredArgsConstructor
@RestController
@RequestMapping(value = "/v1")
@Slf4j
public class SignController {

    private final UserJpaRepo userJpaRepo;

    private final JwtTokenProvider jwtTokenProvider;

    private final ResponseService responseService;

    private final PasswordEncoder passwordEncoder;

    private final KakaoService kakaoService;

    /**
     * 로그인 (signin) 메소드
     * - 로그인 성공시에는 결과로 JWT 토큰 발급
     */
    @ApiOperation(value = "로그인", notes = "이메일로 회원 로그인하는 API")
    @PostMapping(value = "/signin")
    public SingleResult<String> signIn(
            @ApiParam(value = "회원 ID : 이메일", required = true) @RequestParam String id,
            @ApiParam(value = "비밀번호", required = true) @RequestParam String password
    ) {
        //회원 정보 찾기. 없다면 예외 발생
        User user = userJpaRepo.findByUid(id).orElseThrow(CEmailSignInFailedException::new);

        //저장된 비밀번호와, 입력된 비밀번호가 다를 경우에는 예외발생
        if (!passwordEncoder.matches(password, user.getPassword()))
            throw new CEmailSignInFailedException();
//        if (!password.equals(user.getPassword())){
//            throw new CEmailSignInFailedException();
//        }

        return responseService.getSingleResult(jwtTokenProvider.createToken(String.valueOf(user.getMsrl()), user.getRoles()));
    }

    @ApiOperation(value = "회원가입", notes = "회원가입 API")
    @PostMapping(value = "/signup")
    public CommonResult signUp(
            @ApiParam(value = "회원Id : 이메일", required = true) @RequestParam String id,
            @ApiParam(value = "비밀번호", required = true) @RequestParam String password,
            @ApiParam(value = "이름", required = true) @RequestParam String name
    ) {
        userJpaRepo.save(User.builder()
                .uid(id)
                .password(passwordEncoder.encode(password))
                .name(name)
                .roles(Collections.singletonList("ROLE_USER"))
                .build());

        return responseService.getSuccessResult();
    }

    @ApiOperation(value = "소셜 로그인", notes = "소셜 회원 로그인 API")
    @PostMapping(value = "/signin/{provider}")
    public SingleResult<String> signInByProvider(
            @ApiParam(value = "서비스 제공자 provider", required = true, defaultValue = "kakao") @PathVariable String provider,
            @ApiParam(value = "소설 access token", required = true) @RequestParam String accessToken
    ) {
        KakaoProfile profile = kakaoService.getKakaoProfile(accessToken);
        User user = userJpaRepo.findByUidAndProvider(String.valueOf(profile.getId()), provider).orElseThrow(CUserNotFoundException::new);
        return responseService.getSingleResult(jwtTokenProvider.createToken(String.valueOf(user.getMsrl()), user.getRoles()));
    }

    @ApiOperation(value = "소셜 계정 가입", notes = "소셜 계정 회원가입 API")
    @PostMapping(value = "/signup/{provider}")
    public CommonResult signUpProvider (
            @ApiParam (value = "서비스 제공 provider", required = true, defaultValue = "kakao") @PathVariable String provider,
            @ApiParam (value = "소셜 access_token", required = true) @RequestParam String accessToken,
            @ApiParam (value = "이름", required = true) @RequestParam String name
    ) {
        KakaoProfile profile = kakaoService.getKakaoProfile(accessToken);
        String uid = String.valueOf(profile.getId());
        Optional<User> user = userJpaRepo.findByUidAndProvider(uid, provider);
        if(user.isPresent()) { //user가 이미 존재한다면, 회원가입을 할 수 없다.
            throw new CUserExistException();
        }

        userJpaRepo.save(User.builder()
                        .uid(uid)
                        .provider(provider)
                        .name(name)
                        .roles(Collections.singletonList("ROLE_USER"))
                .build());

        return responseService.getSuccessResult();
    }
}
