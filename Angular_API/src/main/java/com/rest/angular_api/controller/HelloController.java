package com.rest.angular_api.controller;

import lombok.Getter;
import lombok.Setter;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class HelloController {
    @GetMapping (value = "/helloworld/string")
    @ResponseBody
    public String hellowroldString () {
        return "helloworld";
    }

    @GetMapping (value = "/helloworld/json")
    @ResponseBody
    public Hello helloworldJson() {
        Hello hello = new Hello();
        hello.message = "helloworld";
        return hello;
    }

    /**
     * buildgradle 에 설정한 ㄹreemarker가 page template으로 사용된다. 그러므로 ftl 파일이 생성되어야한다.
     */
    @GetMapping (value = "/helloworld/page")
    public String helloworld () {
        return "helloworld";
    }

    @Getter
    @Setter
    public static class Hello {
        private String message;
    }

    @GetMapping("/helloworld/long-process")
    @ResponseBody
    public String pause() throws InterruptedException {
        Thread.sleep(10000);
        return "process finished";
    }
}
