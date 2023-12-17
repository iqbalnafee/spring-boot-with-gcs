package com.example.bambergBeverageBox.user.service;

import com.example.bambergBeverageBox.role.model.Role;
import com.example.bambergBeverageBox.role.service.RoleService;
import com.example.bambergBeverageBox.user.model.User;
import com.example.bambergBeverageBox.user.model.UserCreationResponse;
import com.example.bambergBeverageBox.user.model.UserSignUpAddRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor(onConstructor_ = {@Autowired})
public class UserService  implements UserDetailsService {

    private final UserRepository userRepository;
    private final RoleService roleService;
    private final PasswordEncoder passwordEncoder;

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
        user.setPassword(passwordEncoder.encode(userSignUpAddRequest.getSignUpPassword()));

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        LocalDate date = LocalDate.parse(userSignUpAddRequest.getBirthDay(), formatter);

        user.setBirthDay(date);

        return user;
    }

    public UserCreationResponse signIn(UserSignUpAddRequest userSignUpAddRequest) {
        String userName = userSignUpAddRequest.getUserName();
        if (userName != null && !userName.isBlank()) {
            UserDetails userDetails =  loadUserByUsername(userName);
            if (userDetails == null) return getUserCreationResponse("No user name exist!", false);
            else {
                String passWord = userSignUpAddRequest.getSignUpPassword();
                if (!passwordEncoder.matches(passWord, userDetails.getPassword())) {
                    return getUserCreationResponse("Wrong password!", false);
                }
            }
        }
        return getUserCreationResponse("User signed in Successfully", true);
    }

    private Collection<? extends GrantedAuthority> mapRolesToAuthorities(Collection<Role> roles) {
        Collection<? extends GrantedAuthority> mapRoles = roles.stream()
                .map(role -> new SimpleGrantedAuthority(role.getRoleName()))
                .collect(Collectors.toList());
        return mapRoles;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        List<User> users = userRepository.findByUsername(username);
        User user;
        if (!users.isEmpty()) {
            user = users.get(0);
            return new org.springframework.security.core.userdetails.User(user.getUsername(),
                    user.getPassword(),
                    mapRolesToAuthorities(user.getRoles()));
        }else{
            return null;
        }
    }
}
