package com.rest.angular_api.config.security;

import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

/**
 * SpringSecurity Configuration
 * - 서버에 보안 설정을 적용한다.
 * - 아무나 접근 가능한 리소스는 permitAll()로 세팅하고, 나머지 리소스는 'ROLE_USER' 권한이 필요하다고 명시한다.
 * - anyRequest().hasRole("USER") 또는 anyRequest().authenticated()는 동일한 동작이다.
 * - 해당 filter (JwtAuthenticationFilter)는 UsernamePasswordAuthenticationFilter 앞에 설정해야한다.
 * - SpringSecurity 적용 후에는 모든 리소스에 대한 접근이 제한되므로, Swagger 문서 페이지에 대해서는 예외를 적용해야 접근할 수 있다.
 */


@EnableGlobalMethodSecurity(securedEnabled = true, prePostEnabled = true) //annotation 으로 security 권한 설정을 하기위해 활성화. 이 후에 configure메서드에서 authorizeRequest()설정은 주석

@RequiredArgsConstructor
@Configuration
public class SecurityConfiguration extends WebSecurityConfigurerAdapter {

    private final JwtTokenProvider jwtTokenProvider;

    @Bean
    @Override
    public AuthenticationManager authenticationManagerBean() throws Exception {
        return super.authenticationManagerBean();
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
                .httpBasic().disable() //REST API 이므로 기본설정은 사용안한다. 기본설정은 비인증시 로그인폼 화면으로 redirect해주는것이다.
                .csrf().disable() // REST API 이므로 csrf 보안이 필요없다. disable처리
                .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS) //JWT token으로 인증하므로 세션은 필요없다. 생성안한다. (stateless)

                // annotation으로 설정을 바꾸기위해 주석처리
                .and()
                    .authorizeRequests() //다음 리퀘스트에 대한 사용권한 체크
                        .antMatchers("/*/signin", "/*signin/**" , "/*/signup", "/*/signup/**", "/social/**").permitAll() // 가입 및 인증 주소는 누구나 접근 가능
                        .antMatchers(HttpMethod.GET, "/exception/**", "helloworld/**","/actuator/health", "/v1/board/**", "/favicon.ico").permitAll() // helloworld로 시작하는 GET 요청 리소스는 누구나 접근 가능
                    //.antMatchers("/*/users", "/*/user").hasRole("ADMIN")
                    .anyRequest().hasRole("USER") // 그외 나머지 요청은 모두 인증된 회원만 접근 가능

                .and()
                    .exceptionHandling().authenticationEntryPoint(new CustomAuthenticationEntryPoint())

                .and()
                    .exceptionHandling().accessDeniedHandler(new CustomAccessDeniedHandler())

                .and()
                    //jwt token 필터를 id/password 인증 필터 전에 넣기
                    .addFilterBefore(new JwtAuthenticationFilter(jwtTokenProvider), UsernamePasswordAuthenticationFilter.class);
    }



    //ignore check swagger resource. Swagger 문서에 접근하기 위함
    @Override
    public void configure(WebSecurity web) throws Exception {
        web.ignoring().antMatchers(
                "/v2/api-docs",
                "/swagger-resources/**",
                "/swagger-ui.html",
                "/webjars/**",
                "/swagger/**"
        );
    }

}

















