package com.example.bambergBeverageBox.user.service;

import com.example.bambergBeverageBox.user.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository  extends JpaRepository<User, Long> {
    
}
