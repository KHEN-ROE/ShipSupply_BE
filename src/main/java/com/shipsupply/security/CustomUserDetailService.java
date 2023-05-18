package com.shipsupply.security;


import com.shipsupply.domain.User;
import com.shipsupply.persistence.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Optional;

//사용자 정보를 데이터베이스에서 불러오는 데 사용
@Service
public class CustomUserDetailService implements UserDetailsService {

    @Autowired
    UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String username) {
        User u = new User();
        Optional<User> user = userRepository.findByUsername(username);
        if (user.isPresent()) {
            u = user.get();
        }
        return new UserPrincipal(u);
    }

}
