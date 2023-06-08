package com.shipsupply.controller;

import com.shipsupply.domain.User;
import com.shipsupply.service.UserService;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/api/user")
@RequiredArgsConstructor
public class UserController {

    private static final Logger logger = LoggerFactory.getLogger(UserController.class);

    @Autowired
    UserService userService;

    // ResponseEntity는 스프링 프레임워크에서 제공하는 클래스. http 응답의 상태코드, 헤더, 본문 등을 포함하는 정보를 갖는 컨테이너
    //이 클래스를 사용하면 컨트롤러에서 http 응답을 상세하게 조작가능
    //http 요청에 대한 응답을 생성하는 역할
    //회원 정보 조회(관리자만 가능)
    @GetMapping("/inquire")
    public User inquire(@RequestParam String id) {
        return userService.inquire(id);
    }

    @PostMapping("/join")
    public User join(@RequestBody User user) {
        return userService.join(user);
    }

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody User user, HttpServletResponse response) {
        logger.info("로그인 컨트롤러 호출");

        Map<String, Object> map = userService.login(user);

        String token = (String) map.get("token");
        logger.info("생성한 토큰 : " + token);

        Cookie tokenCookie = new Cookie("Authorization", token);
        tokenCookie.setHttpOnly(true);
        tokenCookie.setPath("/"); // 쿠키의 경로를 모든 경로로 설정
        tokenCookie.setDomain("localhost"); // 클라이언트 도메인 설정
        response.addCookie(tokenCookie);
        logger.info("리턴할 response : " + response);

        return ResponseEntity.ok(map);
    }


    @PutMapping("/update")
    public User update(@RequestBody User user) {
        return userService.update(user);
    }

    @DeleteMapping("/delete")
    public void delete(@RequestBody User user) {
        userService.delete(user);
    }
}
