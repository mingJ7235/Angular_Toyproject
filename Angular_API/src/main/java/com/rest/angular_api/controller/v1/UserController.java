package com.rest.angular_api.controller.v1;

import com.rest.angular_api.entity.User;
import com.rest.angular_api.advice.exception.CUserNotFoundException;
import com.rest.angular_api.model.response.CommonResult;
import com.rest.angular_api.model.response.ListResult;
import com.rest.angular_api.model.response.SingleResult;
import com.rest.angular_api.repository.UserJpaRepo;
import com.rest.angular_api.service.ResponseService;
import io.swagger.annotations.*;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

@Api(tags = {"2. User"}) //UserController를 대표하는 최상단 타이틀 영역에 표시될 값을 세팅한다.
@RequiredArgsConstructor
@RestController
@RequestMapping (value = "/v1")
public class UserController {
    private final UserJpaRepo userJpaRepo;
    private final ResponseService responseService;


    @ApiImplicitParams({
            @ApiImplicitParam (name = "X-AUTH-TOKEN", value = "로그인 성공 후 access_token", required = true, dataType = "String", paramType = "header")
    })
    @ApiOperation(value = "회원 조회", notes = "모든 회원 조회 API")
    @GetMapping (value = "/users")
    public ListResult<User> findAllUser () {
//        return userJapRepo.findAll();
        return responseService.getListResult(userJpaRepo.findAll());
    }

    @ApiImplicitParams({
            @ApiImplicitParam (name = "X-AUTH-TOKEN", value = "로그인 성공 후 access_token", required = false, dataType = "String", paramType = "header")
    })
    @ApiOperation(value = "회원 단건 조회", notes = "회원번호 (msrl) 로 회원 조회")
    @GetMapping(value = "/user")
    public SingleResult<User> findUserById (
            @ApiParam (value = "언어", defaultValue = "ko") @RequestParam String lang
    )
    {
        //SecurityContext에서 인증받은 회원의 정보를 얻어온다.
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        String uid = authentication.getName();

        //결과 데이터가 단건일 경우 getSingleResult를 이용하여 결과를 출력
        return responseService.getSingleResult(userJpaRepo.findByUid(uid).orElseThrow(CUserNotFoundException::new));
    }

    @ApiImplicitParams({
            @ApiImplicitParam (name = "X-AUTH-TOKEN", value = "로그인 성공 후 access_token", required = true, dataType = "String", paramType = "header")
    })
    @ApiOperation(value = "회원 수정", notes = "회원 정보 수정 API")
    @PutMapping (value = "/user")
    public SingleResult<User> modify (
        @ApiParam (value = "회원번호", required = true) @RequestParam long msrl,
        @ApiParam (value = "회원이름", required = true) @RequestParam String name
    ){
        User user = User.builder()
                .msrl(msrl)
                .name(name)
                .build();
        return responseService.getSingleResult(userJpaRepo.save(user));
    }

    @ApiImplicitParams({
            @ApiImplicitParam (name = "X-AUTH-TOKEN", value = "로그인 성공 후 access_token", required = true, dataType = "String", paramType = "header")
    })
    @ApiOperation(value = "회원 삭제", notes = "user id로 회원 정보 삭제 API")
    @DeleteMapping (value = "/user/{msrl}")
    public CommonResult delete (
            @ApiParam (value = "회원정보", required = true) @PathVariable long msrl
    ) {
        userJpaRepo.deleteById(msrl);
        return responseService.getSuccessResult();
    }

}
