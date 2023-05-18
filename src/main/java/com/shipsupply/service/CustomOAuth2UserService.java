package com.shipsupply.service;

import com.shipsupply.domain.User;
import com.shipsupply.persistence.UserRepository;
import com.shipsupply.security.UserPrincipal;
import org.hibernate.engine.transaction.jta.platform.internal.SunOneJtaPlatform;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.oauth2.client.userinfo.DefaultOAuth2UserService;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserService;
import org.springframework.security.oauth2.core.OAuth2AuthenticationException;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Service;

@Service
public class CustomOAuth2UserService implements OAuth2UserService<OAuth2UserRequest, OAuth2User> {

    @Autowired
    private UserRepository userRepository;

    @Override
    public OAuth2User loadUser(OAuth2UserRequest userRequest) throws OAuth2AuthenticationException {
        System.out.println("oauth2 loaduser 메서드 호출");
        OAuth2UserService<OAuth2UserRequest, OAuth2User> delegate = new DefaultOAuth2UserService();
        OAuth2User oauth2User = delegate.loadUser(userRequest);

        String provider = userRequest.getClientRegistration().getRegistrationId();
        String providerId = oauth2User.getAttribute("sub");  // "sub"는 Google에서 제공하는 사용자의 고유 ID입니다.
        String email = oauth2User.getAttribute("email");

        // 이미 가입한 사용자인지 확인
        User user = userRepository.findByProviderAndProviderId(provider, providerId)
                .orElseGet(() -> {
                    // 새로운 사용자 등록
                    User newUser = new User();
                    newUser.setProvider(provider);
                    newUser.setProviderId(providerId);
                    newUser.setEmail(email);

                    return userRepository.save(newUser);
                });

        return (OAuth2User) UserPrincipal.create(user, oauth2User.getAttributes());
    }
}
