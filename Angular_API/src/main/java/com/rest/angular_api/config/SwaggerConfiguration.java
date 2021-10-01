package com.rest.angular_api.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

@Configuration
@EnableSwagger2
public class SwaggerConfiguration {
    @Bean
    public Docket swaggerApi () {
        return new Docket(DocumentationType.SWAGGER_2).apiInfo(swaggerInfo()).select()

                //com.rest.api.controller 하단의 Controller 내용을 읽어 mapping된 resource들을 문서화 함
                .apis(RequestHandlerSelectors.basePackage("com.rest.angular_api.controller"))
                .paths(PathSelectors.any())
//                .paths(PathSelectors.ant("/v1/**"))

                .build()
                .useDefaultResponseMessages(false); //기본으로 세팅이되는 200, 401, 403, 404등의 메세지를 표시하지 않음
    }

    private ApiInfo swaggerInfo () {
        return new ApiInfoBuilder().title("Springboot API Documentation for Angular Toy project")
                .description("Angular Toy project Server API Connected Doc")
                .license("Joshua").licenseUrl("http://velog.io/@joshuara7235").version("1").build();
    }
}
