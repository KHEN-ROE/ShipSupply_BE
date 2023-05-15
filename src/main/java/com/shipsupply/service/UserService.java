package com.shipsupply.service;

import ch.qos.logback.core.net.SyslogOutputStream;
import com.shipsupply.domain.User;
import com.shipsupply.persistence.UserRepository;
import com.shipsupply.security.JwtTokenProvider;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UserService {

    @Autowired
    UserRepository ur;

    @Autowired
    BCryptPasswordEncoder encoder;

    @Autowired
    JwtTokenProvider jwtTokenProvider;


    @Value("${jwt.secret}")
    private String secretKey;

    private final Long expiredMs = 1000 * 60 * 60l;

    public String join(User user) {
        Optional<User> findUser = ur.findById(user.getId());
        User u = new User();
        if (findUser.isPresent()) {
            u = findUser.get();
            if (u.getId().equals(user.getId())) {
                throw new RuntimeException("이미 존재하는 아이디입니다.");
            }
        } else {
            u.setId(user.getId());
            u.setUserName(user.getUserName());
            u.setEmail(user.getEmail());
            u.setPassword(encoder.encode(user.getPassword()));
            System.out.println("저장할 user 정보:" + user);
            ur.save(u);
        }
        return "join 호출";
    }

    public String login(User user) {
        try {
            User findUser = ur.findById(user.getId()).get();
            if (!encoder.matches(user.getPassword(), findUser.getPassword())) {
                return "PASSWORD_FAIL";
            }else {
                System.out.println("로그인 성공");
                return JwtTokenProvider.createToken(findUser.getUserName());
            }
        } catch (Exception e) {
            return "USERNAME_FAIL";
        }
    }
}
