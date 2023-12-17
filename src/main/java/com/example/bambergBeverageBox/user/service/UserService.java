package com.example.bambergBeverageBox.user.service;

import com.example.bambergBeverageBox.role.model.Role;
import com.example.bambergBeverageBox.role.service.RoleService;
import com.example.bambergBeverageBox.user.model.User;
import com.example.bambergBeverageBox.user.model.UserCreationResponse;
import com.example.bambergBeverageBox.user.model.UserSignUpAddRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor(onConstructor_ = {@Autowired})
public class UserService {

    private final UserRepository userRepository;
    private final RoleService roleService;
    @Autowired
    private PasswordEncoder passwordEncoder;

    @Transactional
    public UserCreationResponse saveNewUser(UserSignUpAddRequest userSignUpAddRequest) {

        if (userSignUpAddRequest != null) {
            User user = getUserFromUserAddRequest(userSignUpAddRequest);
            if (user.getUsername() != null && !user.getUsername().isBlank()) {
                List<User> userListByUserName = userRepository.findByUsername(user.getUsername());
                if (!userListByUserName.isEmpty()) return getUserCreationResponse("User name already exist!", false);
            }
            if (user.getEmail() != null && !user.getEmail().isBlank()) {
                List<User> userListByEmail = userRepository.findByEmail(user.getEmail());
                if (!userListByEmail.isEmpty()) return getUserCreationResponse("Email already exist!", false);
            }


            user.setRoles(getUserRoles(user));

            userRepository.save(user);
        }

        return getUserCreationResponse("User created Successfully", true);
    }

    public List<Role> getUserRoles(User user) {
        List<Role> roles = new ArrayList<>();
        if (user.getUsername().equals("admin")) {
            Role roleAdmin = roleService.findByRoleName("ROLE_ADMIN");
            roles.add(roleAdmin);
        }

        Role roleCommonUser = roleService.findByRoleName("ROLE_COMMON_USER");
        roles.add(roleCommonUser);
        return roles;
    }

    private UserCreationResponse getUserCreationResponse(String msg, boolean userCreated) {
        return UserCreationResponse.builder().msg(msg).userCreated(userCreated).build();
    }

    public User getUserFromUserAddRequest(UserSignUpAddRequest userSignUpAddRequest) {
        User user = new User();
        user.setFirstName(userSignUpAddRequest.getFirstName());
        user.setLastName(userSignUpAddRequest.getLastName());
        user.setUsername(userSignUpAddRequest.getUserName());
        user.setEmail(userSignUpAddRequest.getEmail());
        user.setPassword(passwordEncoder.encode(userSignUpAddRequest.getUserName()));

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        LocalDate date = LocalDate.parse(userSignUpAddRequest.getBirthDay(), formatter);

        user.setBirthDay(date);

        return user;
    }

}
