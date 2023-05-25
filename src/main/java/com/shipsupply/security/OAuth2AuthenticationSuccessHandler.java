package com.shipsupply.security;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Component
public class OAuth2AuthenticationSuccessHandler extends SimpleUrlAuthenticationSuccessHandler {

    private static final Logger LOGGER = LoggerFactory.getLogger(OAuth2AuthenticationSuccessHandler.class);

    @Autowired
    JwtTokenProvider jwtTokenProvider;

    @Autowired
    public OAuth2AuthenticationSuccessHandler(JwtTokenProvider jwtTokenProvider) {
        this.jwtTokenProvider = jwtTokenProvider;
    }

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response,
                                        Authentication authentication) throws IOException, ServletException {

        String userRole = ((GrantedAuthority) authentication.getAuthorities().toArray()[0]).getAuthority(); // 첫 번째 권한을 가져옴(문자열로)
        String token = JwtTokenProvider.createToken(authentication.getName(), userRole);
        LOGGER.info("생성한 oauth2 토큰: {}" + token);
//         헤더에 토큰 포함하여 전달(토큰 전달 방법은 1. 헤더에 포함 2. 쿠키에 포함 3. 바디에 포함 3개가 있음)
        response.addHeader("Authorization", "Bearer " + token);

        clearAuthenticationAttributes(request);

        // 토큰을 담아서 리다이렉트
        String redirectUrl = "http://localhost:3000/login?token=" + token;

        // 응답을 리다이렉트 URL로 보냄
        response.sendRedirect(redirectUrl);
    }
}
