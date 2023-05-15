package com.shipsupply.persistence;

import com.shipsupply.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, String> {
    User findByUserName(String userName);
    User findByEmail(String email);
    User findByEmailAndPassword(String email, String password);
}
