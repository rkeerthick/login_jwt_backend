package com.example.login_jwt.service;

import com.example.login_jwt.entity.AuthRequest;
import com.example.login_jwt.entity.UserInfo;
import com.example.login_jwt.repository.UserInfoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class UserInfoService implements UserDetailsService {

    @Autowired
    private UserInfoRepository repository;

    @Autowired
    private JwtService jwtService;

    @Lazy
    @Autowired
    private PasswordEncoder encoder;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        System.out.println(username);
        Optional<UserInfo> userDetail = Optional.ofNullable(repository.findByEmail(username));
        // Converting userDetail to UserDetails
        return userDetail.map(UserInfoDetails::new)
                .orElseThrow(() -> new UsernameNotFoundException("User not found " + username));
    }

    public String addUser(UserInfo userInfo) {
        userInfo.setPassword(encoder.encode(userInfo.getPassword()));
        UserInfo userInfo1 = repository.findByEmail(userInfo.getEmail());
        if(userInfo1 == null) {
            repository.save(userInfo);
            return jwtService.generateToken(userInfo.getEmail());
        }
        return "User already exists";
    }


    public String authUser(AuthRequest authRequest) {
        UserInfo userInfo = repository.findByEmail(authRequest.getEmail());
        if(userInfo == null) return "email not found";
        return jwtService.generateToken(authRequest.getEmail());
    }

    public List<UserInfo> getAllUsers() {
        System.out.println("list atsr");

        return repository.findAll();
    }

    public UserInfo getByEmail(String email) {
        return repository.findByEmail(email);
    }

    public String updateUser(UserInfo userInfo) {
        System.out.println(userInfo.getEmail());
        UserInfo userInfo1 = repository.findByEmail(userInfo.getEmail());
        userInfo1.setFirstName(userInfo.getFirstName());
        userInfo1.setLastName(userInfo.getLastName());
        userInfo1.setEmail(userInfo.getEmail());
        userInfo1.setDob(userInfo.getDob());
        userInfo1.setPhoneNumber(userInfo.getPhoneNumber());
        repository.save(userInfo1);
        return "successfully updated";

    }
}
