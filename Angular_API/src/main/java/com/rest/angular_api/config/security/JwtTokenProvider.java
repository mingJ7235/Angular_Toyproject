package com.rest.angular_api.config.security;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jws;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import javax.servlet.http.HttpServletRequest;
import java.util.Base64;
import java.util.Date;
import java.util.List;

/**
 * JWT Token 생성 및 유효성 검증 컴포넌트
 * - JWT는 여러가지 암호화 알고리즘을 제공하며 알고리즘 (SignatureAlgorithm.XXXXX)과 비밀키 (secretKey) 를 가지고 토큰을 생성한다.
 * -
 */
@RequiredArgsConstructor
@Component
public class JwtTokenProvider { //JWT 토큰을 생성 및 검증 모듈

    @Value("spring.jwt.secret")
    private String secretKey;

    private long tokenValidMilisecond = 1000L * 60 * 60; //1시간만 토큰 유효하게끔

    private final UserDetailsService userDetailsService; //security 에 있는 것

    @PostConstruct
    protected void init() {
        secretKey = Base64.getEncoder().encodeToString(secretKey.getBytes());
    }

    //JWT Token 생성
    public String createToken (String userPk, List<String> roles) {
        Claims claims = Jwts.claims().setSubject(userPk);
        claims.put("roles", roles);
        Date now = new Date();
        return Jwts.builder()
                .setClaims(claims) //데이터
                .setIssuedAt(now) // Token 발행일자
                .setExpiration(new Date(now.getTime() + tokenValidMilisecond)) // token expire time 세팅
                .signWith(SignatureAlgorithm.HS256, secretKey) // Encryption 알고리즘, secret 값 세팅
                .compact();
    }

    //JWT Token 으로 인증정보 조회
    public Authentication getAuthentication (String token) {
        UserDetails userDetails = userDetailsService.loadUserByUsername(this.getUserPk(token));
        return new UsernamePasswordAuthenticationToken(userDetails, "", userDetails.getAuthorities());
    }

    // JWT Token 에서 회원 구별 정보 추출
    public String getUserPk (String token) {
        return Jwts.parser().setSigningKey(secretKey).parseClaimsJws(token).getBody().getSubject();
    }

    // Request 의 Header에서 token 파싱 : "X-AUTH-TOKEN : jwt 토큰"
    public String resolveToken (HttpServletRequest req) {
        return req.getHeader("X-AUTH-TOKEN");
    }

    //Jwt 토큰의 유효성 + 만료일자 확인
    public boolean validateToken (String jwtToken) {
        try {
            Jws<Claims> claims = Jwts.parser().setSigningKey(secretKey).parseClaimsJws(jwtToken);
            return !claims.getBody().getExpiration().before(new Date());
        }catch (Exception e) {
            return false;
        }
    }
}

















