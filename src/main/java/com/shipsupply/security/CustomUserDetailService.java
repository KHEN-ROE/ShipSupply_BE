package com.shipsupply.security;


import com.shipsupply.domain.User;
import com.shipsupply.persistence.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Service;

import java.util.Optional;

//사용자 정보를 데이터베이스에서 불러오는 데 사용
@RequiredArgsConstructor
@Service
public class CustomUserDetailService implements UserDetailsService {

    @Autowired
    UserRepository userRepository;

    public UserDetails loadUserByUsername(String userPk) {
        Optional<User> findUser = userRepository.findById(userPk);
        User user = findUser.get();
        return null;
    }
}