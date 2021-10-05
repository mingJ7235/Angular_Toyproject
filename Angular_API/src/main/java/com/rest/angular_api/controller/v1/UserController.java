package com.rest.angular_api.controller.v1;

import com.rest.angular_api.entity.member.User;
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

//annotation으로 controller에 security 권한 설정 Controller 내부의 모든 리소스에 대하여 일괄로 동일한 권한을 설정하는 경우 controller 상단에 이렇게 annotation 세팅
//만약, 리소스별로 다른 권한 설정의 경우, 각각의 메소드 위에 annotation 설정가능
// annotation으로 권한을 설정한 리소스외 나머지 리소스들은 누구나 접근 가능한 리소스로 설정된다.
//@PreAuthorize("hasRole('ROLE_USER')") //또는 @Secured("ROLE_USER")

@Api(tags = {"2. User"}) //UserController를 대표하는 최상단 타이틀 영역에 표시될 값을 세팅한다.
@RequiredArgsConstructor
@RestController
@RequestMapping (value = "/v1")
public class UserController {
    private final UserJpaRepo userJpaRepo;
    private final ResponseService responseService;


    //@Secured("ROLE_USER") 각각의 리소스마다 security권한을 설정해야하면 해당 메서드 위에 이렇게 annotation 세팅
    @ApiImplicitParams({
            @ApiImplicitParam (name = "X-AUTH-TOKEN", value = "로그인 성공 후 access_token", required = true, dataType = "String", paramType = "header")
    })
    @ApiOperation(value = "회원 조회", notes = "모든 회원 조회 API")
    @GetMapping (value = "/users")
    public ListResult<User> findAllUser () {
//        return userJapRepo.findAll();
        return responseService.getListResult(userJpaRepo.findAll());
    }

    //@PreAuthorize("hasRole(ROLE_ADMIN)")
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
