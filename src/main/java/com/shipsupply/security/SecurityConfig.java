package com.shipsupply.security;

import com.shipsupply.service.CustomOAuth2UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

//    @Autowired
//    JwtTokenProvider jwtTokenProvider;

    private final JwtTokenProvider jwtTokenProvider;
    private final OAuth2AuthenticationSuccessHandler oAuth2AuthenticationSuccessHandler;
    private final CustomOAuth2UserService customOAuth2UserService;

    @Autowired
    public SecurityConfig(JwtTokenProvider jwtTokenProvider, OAuth2AuthenticationSuccessHandler oAuth2AuthenticationSuccessHandler, CustomOAuth2UserService customOAuth2UserService) {
        this.jwtTokenProvider = jwtTokenProvider;
        this.oAuth2AuthenticationSuccessHandler = oAuth2AuthenticationSuccessHandler;
        this.customOAuth2UserService = customOAuth2UserService;
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {

            http
                // rest api이므로 기본설정 안함. 기본설정은 비인증 시 로그인 폼 화면으로 리다이렉트 된다.
                .httpBasic().disable()
                .csrf().disable()
                // jwt token으로 생성하므로 세션은 필요 없으므로 생성 안함.
                .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                .and()
                .authorizeRequests() // 다음 리퀘스트에 대한 사용권한 체크
                //  Spring Security 필터 체인을 통과하지만
                    //인증 없이도 접근이 허용됨. 사용자는 이 경로로 요청을 보낼 때 별도로 인증 절차를 거치지 않아도 됨
                .antMatchers("/", "/oauth2/**", "/api/user/join", "/api/user/login").permitAll()
                // 그 외 나머지 요청은 모두 인증된 회원만 접근 가능
                .anyRequest().authenticated()
                    .and()
                    .oauth2Login()
                    .userInfoEndpoint()
                    .userService(customOAuth2UserService)
                    .and()
                    .successHandler(oAuth2AuthenticationSuccessHandler)
                    .failureUrl("/loginFailure")
                .and()
                // jwt token 필터를 id/password 인증 필터 전에 넣는다.
                    // JWT 토큰을 이용한 인증을 처리하기 위한 필터
                    // 요청이 들어올 때마다 JWT 토큰을 확인하여 사용자를 인증.
                .addFilterBefore(new JwtAuthenticationFilter(jwtTokenProvider), // antMatchers().permitAll()에 있는 요청들은 이 필터 거치지 않는다
                        UsernamePasswordAuthenticationFilter.class);
            return http.build();
    }
}