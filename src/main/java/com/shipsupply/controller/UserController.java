package com.shipsupply.controller;

import com.shipsupply.domain.User;
import com.shipsupply.service.UserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletResponse;
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
    @Operation(summary = "사용자 정보 조회")
    @ApiResponse(responseCode = "200", description = "사용자 정보가 정상적으로 조회되었습니다.")
    @GetMapping("/inquire")
    public User inquire(@RequestParam String id) {
        return userService.inquire(id);
    }

    @Operation(summary = "회원가입")
    @ApiResponse(responseCode = "200", description = "회원가입에 성공했습니다.")
    @PostMapping("/join")
    public User join(@RequestBody User user) {
        return userService.join(user);
    }

    @Operation(summary = "사용자 로그인")
    @ApiResponses(@ApiResponse(responseCode = "200", description = "사용자가 정상적으로 로그인되었습니다."))
    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody User user, HttpServletResponse response) {
        logger.info("로그인 컨트롤러 호출");

        Map<String, Object> map = userService.login(user);

        String accessToken = (String) map.get("accessToken");
        String refreshToken = (String) map.get("refreshToken");

        Cookie accessTokenCookie = new Cookie("accessToken", accessToken);
        accessTokenCookie.setHttpOnly(true);
        accessTokenCookie.setPath("/"); // 쿠키의 경로를 모든 경로로 설정
        accessTokenCookie.setDomain("localhost"); // 클라이언트 도메인 설정

        Cookie refreshTokenCookie = new Cookie("refreshToken", refreshToken);
        refreshTokenCookie.setHttpOnly(true);
        refreshTokenCookie.setPath("/");
        refreshTokenCookie.setDomain("localhost");

        response.addCookie(accessTokenCookie);
        response.addCookie(refreshTokenCookie);

        logger.info("리턴할 response : " + response);

        return ResponseEntity.ok(map);
    }

    @Operation(summary = "사용자 로그아웃")
    @ApiResponses(@ApiResponse(responseCode = "200", description = "사용자가 정상적으로 로그아웃되었습니다."))
    @PostMapping("logout")
    public ResponseEntity<?> logout(@RequestBody User user, HttpServletResponse response) {
        logger.info("logout 호출");

        Cookie acccessTokenCookie = new Cookie("accessToken", null); // 토큰 담겨있는 HttpOnly쿠키 삭제
        acccessTokenCookie.setMaxAge(0); // 쿠키 즉시 만료되도록 설정
        acccessTokenCookie.setHttpOnly(true);
        acccessTokenCookie.setPath("/"); // 쿠키의 경로를 모든 경로로 설정

        Cookie refreshTokenCookie = new Cookie("refreshToken", null); // 토큰 담겨있는 HttpOnly쿠키 삭제
        refreshTokenCookie.setMaxAge(0); // 쿠키 즉시 만료되도록 설정
        refreshTokenCookie.setHttpOnly(true);
        refreshTokenCookie.setPath("/"); // 쿠키의 경로를 모든 경로로 설정


        Cookie userIdCookie = new Cookie("userId", null); // userId 있는 쿠키 삭제
        userIdCookie.setMaxAge(0);
        userIdCookie.setPath("/");

        response.addCookie(acccessTokenCookie); // 응답에 쿠키 추가
        response.addCookie(refreshTokenCookie);
        response.addCookie(userIdCookie);

        return ResponseEntity.ok().build(); // 상태코드 200과 빈 본문을 가진 응답 반환
    }

    @Operation(summary = "회원정보 수정")
    @ApiResponses(@ApiResponse(responseCode = "200", description = "사용자 정보가 정상적으로 업데이트되었습니다."))
    @PutMapping("/update")
    public User update(@RequestBody User user) {
        return userService.update(user);
    }

    @Operation(summary = "회원정보 삭제")
    @ApiResponses(@ApiResponse(responseCode = "200", description = "사용자 정보가 정상적으로 삭제되었습니다."))
    @DeleteMapping("/delete")
    public void delete(@RequestBody User user) {
        userService.delete(user);
    }
}
