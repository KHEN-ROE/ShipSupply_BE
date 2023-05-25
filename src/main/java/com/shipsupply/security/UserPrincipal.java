package com.shipsupply.security;

import com.shipsupply.domain.User;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.oauth2.core.user.OAuth2User;

import java.util.Collection;
import java.util.Collections;
import java.util.Map;

public class UserPrincipal implements UserDetails, OAuth2User {

    private final User user;
    private Map<String, Object> attributes;

    public UserPrincipal(User user) {
        this.user = user;
        this.attributes = null; // null로 초기화합니다.
    }

    public UserPrincipal(User user, Map<String, Object> attributes) {
        this.user = user;
        this.attributes = attributes;
    }

    public User getUser() {
        return user;
    }

    public static UserPrincipal create(User user, Map<String, Object> attributes) {
        UserPrincipal userPrincipal = new UserPrincipal(user);
        userPrincipal.setAttributes(attributes);
        return userPrincipal;
    }

    @Override
    public Map<String, Object> getAttributes() {
        return attributes;
    }

    public void setAttributes(Map<String, Object> attributes) {
        this.attributes = attributes;
    }

    @Override
    public String getName() {
        return user.getId();
    }

    //사용자가 가지고 있는 권한(롤)을 반환하는 메서드.
    //Spring Security에서는 권한을 GrantedAuthority 인터페이스를 통해 표현
    //예를 들어, 'ROLE_USER', 'ROLE_ADMIN' 등이 될 수 있음
    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        System.out.println("getAuthorities 메서드 호출");

        return Collections.singleton(new SimpleGrantedAuthority(user.getRole()));

//        SimpleGrantedAuthority authority = new SimpleGrantedAuthority(user.getRole());
//        return Collections.singletonList(authority);

        //해당 사용자가 'ROLE_USER', 'ROLE_ADMIN' 등 하나의 권한만을 가지고 있음을 의미
//        return Collections.singleton(user::getRole);
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
