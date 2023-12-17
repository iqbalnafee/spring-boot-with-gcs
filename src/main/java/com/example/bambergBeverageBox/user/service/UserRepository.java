package com.example.bambergBeverageBox.user.service;

import com.example.bambergBeverageBox.user.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface UserRepository  extends JpaRepository<User, Long> {
    List<User> findByUsername(String userName);
    List<User> findByEmail(String email);
}
