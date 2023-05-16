package com.shipsupply.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

import javax.servlet.ServletException;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Component
public class OAuth2AuthenticationSuccessHandler extends SimpleUrlAuthenticationSuccessHandler {

    @Autowired
    JwtTokenProvider jwtTokenProvider;

    @Autowired
    public OAuth2AuthenticationSuccessHandler(JwtTokenProvider jwtTokenProvider) {
        this.jwtTokenProvider = jwtTokenProvider;
    }

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response,
                                        Authentication authentication) throws IOException, ServletException {
        String token = JwtTokenProvider.createToken(authentication.getName());
        System.out.println("oauth2 토큰 : " + token);
        // 헤더에 토큰 포함하여 전달(토큰 전달 방법은 1. 헤더에 포함 2. 쿠키에 포함 3. 바디에 포함 3개가 있음)
//        response.addHeader("Authorization", "Bearer " + token);

        // 쿠키에 토큰 담아서 보낸다
        // 쿠키 생성
        Cookie cookie = new Cookie("token", token);
        cookie.setHttpOnly(true); // HttpOnly 설정
        cookie.setSecure(true); // HTTPS 환경에서만 전송되도록 Secure 설정
        cookie.setPath("/"); // 쿠키의 유효 범위 설정
        response.addCookie(cookie); // 응답에 쿠키 추가

        clearAuthenticationAttributes(request);
        // 리다이렉트 URL 설정
//        String redirectUrl = "http://localhost:3000?token=" + token;
        String redirectUrl = "http://localhost:3000";

        // 응답을 리다이렉트 URL로 보냄
        response.sendRedirect(redirectUrl);
    }
}
