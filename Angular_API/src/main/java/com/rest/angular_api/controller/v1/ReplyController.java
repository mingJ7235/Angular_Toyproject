package com.rest.angular_api.controller.v1;

import io.swagger.annotations.Api;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Api(tags = {"4. Reply"})
@RestController
@RequiredArgsConstructor
@RequestMapping(value = "/v1/reply")
public class ReplyController {

}
