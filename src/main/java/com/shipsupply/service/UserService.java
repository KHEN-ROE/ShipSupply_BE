package com.shipsupply.service;

import com.shipsupply.domain.User;
import com.shipsupply.persistence.UserRepository;
import com.shipsupply.security.JwtTokenProvider;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
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

        Optional<User> findUser = ur.findById(user.getId());
        if(findUser.isPresent()) {
            User u = findUser.get();
            if(encoder.matches(user.getPassword(), u.getPassword())){
                System.out.println("로그인 성공");
                String token = JwtTokenProvider.createToken(u.getUserName());
                System.out.println("토큰 : " + token);
                return token;
            }else {
                throw new RuntimeException("비밀번호 불일치");
            }
        }else {
            throw new RuntimeException("존재하지 않는 회원");
        }
    }
}
