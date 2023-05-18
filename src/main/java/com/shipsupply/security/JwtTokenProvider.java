package com.shipsupply.security;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jws;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import javax.annotation.PostConstruct;
import javax.servlet.http.HttpServletRequest;
import java.util.Base64;
import java.util.Date;

@Component
public class JwtTokenProvider { // JWT 토큰을 생성 및 검증 모듈

    private static String secretKey = "ASDasdasd1dasd_dqwdqw1dsad789";

    @Autowired
    UserDetailsService userDetailsService;

    @PostConstruct
    protected void init() {
        secretKey = Base64.getEncoder().encodeToString(secretKey.getBytes());
    }

    // Jwt 토큰 생성
    public static String createToken(String userPk) {
        Claims claims = Jwts.claims().setSubject(userPk);
        Date now = new Date();
        // 1시간 토큰 유효
        long tokenValidMillisecond = 1000L * 60 * 60;
        return Jwts.builder()
                .setClaims(claims) // 데이터
                .setIssuedAt(now) // 토큰 발행일자
                .setExpiration(new Date(now.getTime() + tokenValidMillisecond)) // 토큰 유효시간 설정
                .signWith(SignatureAlgorithm.HS256, secretKey) // 암호화 알고리즘, 암호키
                .compact();
    }

    // Jwt 토큰으로 인증 정보 조회
    public Authentication getAuthentication(String token) {
        UserDetails userDetails = userDetailsService.loadUserByUsername(this.getUserPk(token));
        return new UsernamePasswordAuthenticationToken(userDetails, "", userDetails.getAuthorities());
    }

    // Jwt 토큰에서 회원 구별 정보 추출
    public String getUserPk(String token) {
        return Jwts.parser().setSigningKey(secretKey).parseClaimsJws(token).getBody().getSubject();
    }

    // Request의 Header에서 token 파싱
    public String resolveToken(HttpServletRequest req) {
        System.out.println("resolveToken 호출");
        String token =  req.getHeader("Authorization");
        if (StringUtils.hasText(token) && token.startsWith("Bearer ")) {
            token = token.substring(7, token.length());
            System.out.println("리액트에서 준 토큰 : " + token);
            return token;
        }
        return token;
//        String token = req.getHeader("AUTH-TOKEN");
//        System.out.println("리액트에서 준 토큰 : " + token);
//        return token;
    }

    // Jwt 토큰의 유효성 + 만료일자 확인
    public boolean validateToken(String jwtToken) {
        try {
            Jws<Claims> claims = Jwts.parser().setSigningKey(secretKey).parseClaimsJws(jwtToken);
            return !claims.getBody().getExpiration().before(new Date());
        } catch (Exception e) {
            return false;
        }
    }
}