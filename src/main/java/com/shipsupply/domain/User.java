package com.shipsupply.domain;

import lombok.*;

import javax.persistence.*;

@Entity
@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class User {

    @Id
    @Column(name = "id")
    String id;
    String password;
    String email;
    String username;
    String role;
    
    //소셜 로그인 서비스를 제공하는 업체(구글, 페이스북, 깃허브 등)
    String provider; // 이걸 다른 테이블로 만들어야할 듯
    // 사용자의 소셜로그인 아이디
    String providerId;

}
