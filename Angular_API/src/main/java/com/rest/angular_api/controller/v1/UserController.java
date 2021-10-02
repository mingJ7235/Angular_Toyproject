package com.rest.angular_api.controller.v1;

import com.rest.angular_api.entity.User;
import com.rest.angular_api.exception.CUserNotFoundException;
import com.rest.angular_api.model.response.CommonResult;
import com.rest.angular_api.model.response.ListResult;
import com.rest.angular_api.model.response.SingleResult;
import com.rest.angular_api.repository.UserJpaRepo;
import com.rest.angular_api.service.ResponseService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@Api(tags = {"2. User"}) //UserController를 대표하는 최상단 타이틀 영역에 표시될 값을 세팅한다.
@RequiredArgsConstructor
@RestController
@RequestMapping (value = "/v1")
public class UserController {
    private final UserJpaRepo userJpaRepo;
    private final ResponseService responseService;

    @ApiOperation(value = "회원 조회", notes = "모든 회원 조회 API")
    @GetMapping (value = "/user")
    public ListResult<User> findAllUser () {
//        return userJapRepo.findAll();
        return responseService.getListResult(userJpaRepo.findAll());
    }

    @ApiOperation(value = "회원 단건 조회", notes = "userId로 회원 조회")
    @GetMapping(value = "/user/{msrl}")
    public SingleResult<User> findUserById (
            @ApiParam (value = "회원 ID", required = true) @PathVariable long msrl,
            @ApiParam (value = "언어", defaultValue = "ko") @RequestParam String lang
    )
    {
        return responseService.getSingleResult(userJpaRepo.findById(msrl).orElseThrow(CUserNotFoundException::new));
    }

    @ApiOperation(value = "회원 입력", notes = "회원 등록 API")
    @PostMapping (value = "/user")
    public User save (
            @ApiParam(value = "회원아이디", required = true) @RequestParam String uid,
            @ApiParam(value = "회원 이름", required = true) @RequestParam String name
    ) {
        User user = User.builder()
                .uid(uid)
                .name(name)
                .build();
        return userJpaRepo.save(user);
    }

    @ApiOperation(value = "회원 수정", notes = "회원 정보 수정 API")
    @PutMapping (value = "/user")
    public SingleResult<User> modify (
        @ApiParam (value = "회원번호", required = true) @RequestParam long msrl,
        @ApiParam (value = "회원아이디", required = true) @RequestParam String uid,
        @ApiParam (value = "회원 이름", required = true) @RequestParam String name
    ){
        User user = User.builder()
                .msrl(msrl)
                .uid(uid)
                .name(name)
                .build();
        return responseService.getSingleResult(userJpaRepo.save(user));
    }

    @ApiOperation(value = "회원 삭제", notes = "user id로 회원 정보 삭제 API")
    @DeleteMapping (value = "/user/{msrl}")
    public CommonResult delete (
            @ApiParam (value = "회원정보", required = true) @PathVariable long msrl
    ) {
        userJpaRepo.deleteById(msrl);
        return responseService.getSuccessResult();
    }

}
