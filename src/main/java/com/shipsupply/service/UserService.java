package com.shipsupply.service;

import com.shipsupply.domain.User;
import com.shipsupply.persistence.UserRepository;
import com.shipsupply.security.JwtTokenProvider;
import com.shipsupply.security.UserPrincipal;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.codec.Decoder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import javax.servlet.ServletOutputStream;
import java.util.Base64;
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

    public User inquire(User user) {
        Optional<User> findUser = ur.findById(user.getId());
        User u = new User();
        if (findUser.isPresent()) {
            u = findUser.get();
            if(u.getRole().equals("admin")){
                return u;
            }
        }
        return null;
    }

    public String join(User user) {
        Optional<User> findUser = ur.findById(user.getId());
        if (findUser.isPresent()) {
            throw new RuntimeException("이미 존재하는 아이디");
        }else {
            User u = new User();
            u.setId(user.getId());
            u.setEmail(user.getEmail());
            u.setPassword(encoder.encode(user.getPassword()));
            u.setUsername(user.getUsername());
            u.setRole(user.getRole());
            ur.save(u);
        }
        return "join 호출";
    }

    public String login(User user) {

        Optional<User> findUser = ur.findById(user.getId());
        if (findUser.isPresent()) {
            User u = findUser.get();
            if (encoder.matches(user.getPassword(), u.getPassword())) {
                System.out.println("받은 로그인 정보 : " + user);
                System.out.println("로그인 성공");
                System.out.println("토큰에 저장할 정보 : " + u.getUsername() + "," + u.getRole());
                String token = JwtTokenProvider.createToken(u.getUsername(), u.getRole());
                System.out.println("생성한 토큰 : " + token);
                return token;
            } else {
                System.out.println("받은 로그인 정보 : " + user);
                throw new RuntimeException("비밀번호 불일치");
            }
        } else {
            throw new RuntimeException("존재하지 않는 회원");
        }
    }

    public User update(User user) {
        Optional<User> findUser = ur.findById(user.getId());
        if (findUser.isPresent()) {
            User u = findUser.get();
            if (encoder.matches(user.getPassword(), u.getPassword())) {
                u.setPassword(user.getPassword());
                u.setEmail(user.getEmail());
                u.setUsername(user.getUsername());
                return ur.save(u);
            }else {
                throw new RuntimeException("권한이 없습니다.");
            }
        }
        return null;
    }

    public void delete(User user) {
        System.out.println("user 비밀번호 : " + user.getPassword());
        Optional<User> findUser = ur.findById(user.getId());
        if (findUser.isPresent()) {
            User u = findUser.get();
            System.out.println("findUser 비밀번호 : " + u.getPassword()); // db에있는 암호화된 비번 가져옴. 반면 받는 건 그냥 raw비번
            if (encoder.matches(user.getPassword(), u.getPassword())) {
                ur.deleteById(user.getId());
            }else {
                throw new RuntimeException("권한이 없습니다.");
            }
        }
    }
}
