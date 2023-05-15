package com.shipsupply.security;

import lombok.RequiredArgsConstructor;
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
@RequiredArgsConstructor
public class SecurityConfig {

    @Autowired
    JwtTokenProvider jwtTokenProvider;

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {

            http
                // rest api이므로 기본설정 안함. 기본설정은 비인증 시 로그인 폼 화면으로 리다이렉트 된다.
                .httpBasic().disable()
                .csrf().disable()
                // jwt token으로 생성하므로 세션은 필요 없으므로 생성 안함.
                .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                    .and()
                    .oauth2Login() // 시큐리티가 OAuth2.0 로그인 과정 처리하도록 설정 - 이게 맨 처음 와야되는 것 같은데
                    .defaultSuccessUrl("http://localhost:3000", true) // 로그인 성공시 이동할 URL을 설정
                    // 이 때, 스프링 시큐리티는 구글 인증 서버로부터 받은 인증 코드를 이용해
                    // 구글 OAuth 2.0 인증 서버에 액세스 토큰을 요청하고 얻어서 사용자정보 얻어옴
                    // 클라이언트 ID와 시크릿이 사용됨
                    // 스프링 시큐리티가 이 과정을 자동으로 처리
                    .failureUrl("/loginFailure")
                .and()
                .authorizeRequests() // 다음 리퀘스트에 대한 사용권한 체크
                // 회원가입과 로그인은 인증 없어도 접근 가능. 그래서 필터에 아직 안 걸렸다?
                .antMatchers("/", "/oauth2/**", "/api/user/join", "/api/user/login").permitAll()
                // 그 외 나머지 요청은 모두 인증된 회원만 접근 가능
                .anyRequest().authenticated()
                
                .and()
                // jwt token 필터를 id/password 인증 필터 전에 넣는다.
                .addFilterBefore(new JwtAuthenticationFilter(jwtTokenProvider),
                        UsernamePasswordAuthenticationFilter.class);

            return http.build();
    }

}