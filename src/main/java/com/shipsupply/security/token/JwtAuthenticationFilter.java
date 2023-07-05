package com.shipsupply.security.token;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.GenericFilterBean;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class JwtAuthenticationFilter extends GenericFilterBean {

    private static final Logger logger = LoggerFactory.getLogger(JwtAuthenticationFilter.class);

    @Autowired
    JwtTokenProvider jwtTokenProvider; // JWT 토큰을 생성 및 검증 모듈 클래스

    // Jwt Provider 주입
    public JwtAuthenticationFilter(JwtTokenProvider jwtTokenProvider) {
        this.jwtTokenProvider = jwtTokenProvider;
    }

    // Request로 들어오는 Jwt Token의 유효성을 검증 (jwtTokenProvider.validateToken)라는
    // filter를 filterChain에 등록한다.
    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain filterChain)
            throws IOException, ServletException {

        logger.info("doFilter 호출");
        // resolveToken : Request의 Header에서 token 파싱
//        String token = jwtTokenProvider.resolveToken((HttpServletRequest) request);
        String accessToken = jwtTokenProvider.resolveTokenFromCookie((HttpServletRequest) request, "accessToken");
        String refreshToken = jwtTokenProvider.resolveTokenFromCookie((HttpServletRequest) request, "refreshToken");

        // validateToken : Jwt 토큰의 유효성 + 만료일자 확인
        if (accessToken != null && jwtTokenProvider.validateToken(accessToken)) { // 로그인 요청시 토큰이 없어도 permitAll 돼있어서 여길 통과함
            // getAuthentication : Jwt 토큰으로 인증 정보 조회
            Authentication auth = jwtTokenProvider.getAuthentication(accessToken);
            SecurityContextHolder.getContext().setAuthentication(auth);
        } else if (refreshToken != null && jwtTokenProvider.validateToken(refreshToken)) {
            // 리프레시 토큰이 유효하면 새로운 엑세스 토큰을 발급하고 응답에 넣어줌
            String username = jwtTokenProvider.getUserPk(refreshToken);
            String role = jwtTokenProvider.getRole(refreshToken);
            String newAccessToken = JwtTokenProvider.createToken(username, role, 1000L * 60);

            Cookie accessTokenCookie = new Cookie("accessToken", newAccessToken);
            accessTokenCookie.setHttpOnly(true);
            accessTokenCookie.setPath("/");
            accessTokenCookie.setDomain("localhost");
            ((HttpServletResponse) response).addCookie(accessTokenCookie);
        }
        filterChain.doFilter(request, response);
    }
}