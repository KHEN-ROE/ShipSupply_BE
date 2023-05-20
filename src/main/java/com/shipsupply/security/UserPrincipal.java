package com.shipsupply.security;

import com.shipsupply.domain.User;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.Collections;
import java.util.Map;

public class UserPrincipal implements UserDetails {
    private final User user;

    public UserPrincipal(User user) {
        this.user = user;
    }

    public User getUser() {
        return user;
    }

    public static UserPrincipal create(User user, Map<String, Object> attributes) {
        return new UserPrincipal(user);
    }

    //사용자가 가지고 있는 권한(롤)을 반환하는 메서드.
    //Spring Security에서는 권한을 GrantedAuthority 인터페이스를 통해 표현
    //예를 들어, 'ROLE_USER', 'ROLE_ADMIN' 등이 될 수 있음
    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        //해당 사용자가 'ROLE_USER', 'ROLE_ADMIN' 등 하나의 권한만을 가지고 있음을 의미
        return Collections.singleton(user::getRole);
    }

    @Override
    public String getPassword() {
        return user.getPassword();
    }

    @Override
    public String getUsername() {
        return user.getUsername();
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }
}
