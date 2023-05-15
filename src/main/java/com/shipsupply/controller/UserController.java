package com.shipsupply.controller;

import com.shipsupply.domain.User;
import com.shipsupply.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@CrossOrigin(origins = "http://localhost:3000")
@RestController
@RequestMapping("/api/user")
@RequiredArgsConstructor
public class UserController {

    @Autowired
    UserService userService;

    @PostMapping("/join")
    public ResponseEntity<String> join(@RequestBody User user) {
        System.out.println("회원가입 정보 : " + user);
        return ResponseEntity.ok().body(userService.join(user));
    }

    @PostMapping("/login")
    public ResponseEntity<String> login(@RequestBody User user) {
        System.out.println("로그인 정보 : " + user);
        return ResponseEntity.ok().body(userService.login(user));
    }

}
