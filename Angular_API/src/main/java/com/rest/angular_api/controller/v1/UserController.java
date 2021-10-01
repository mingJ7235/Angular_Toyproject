package com.rest.angular_api.controller.v1;

import com.rest.angular_api.entity.User;
import com.rest.angular_api.repository.UserJapRepo;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Api(tags = {"1. User"}) //UserController를 대표하는 최상단 타이틀 영역에 표시될 값을 세팅한다.
@RequiredArgsConstructor
@RestController
@RequestMapping (value = "/v1")
public class UserController {
    private final UserJapRepo userJapRepo;

    @ApiOperation(value = "회원 조회", notes = "모든 회원 조회 API")
    @GetMapping (value = "/user")
    public List<User> findAllUser () {
        return userJapRepo.findAll();
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
        return userJapRepo.save(user);
    }
}
