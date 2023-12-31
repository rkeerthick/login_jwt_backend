package com.example.login_jwt.controller;

import com.example.login_jwt.entity.AuthRequest;
import com.example.login_jwt.entity.UserInfo;
import com.example.login_jwt.service.JwtService;
import com.example.login_jwt.service.UserInfoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@RestController
@RequestMapping("/auth")
@CrossOrigin
public class UserController {

    @Autowired
    private UserInfoService service;

   @Autowired
   private JwtService jwtService;

    @Autowired
    private AuthenticationManager authenticationManager;

    @GetMapping("/welcome")
    public String welcome() {
        return "Welcome this endpoint is not secure";
    }

    @PostMapping("/addNewUser")
    public String addNewUser(@RequestBody UserInfo userInfo) {
        return service.addUser(userInfo);
    }

    @PostMapping("/getUser")
    public String getUserByEmail(@RequestBody AuthRequest authRequest) {
        System.out.println("1");
        System.out.println(authRequest);
        return service.authUser(authRequest); }

    @GetMapping("/user/userProfile")
    @PreAuthorize("hasAuthority('ROLE_USER')")
    public String userProfile() {
        return "Welcome to User Profile";
    }

    @GetMapping("/admin/adminProfile")
    @PreAuthorize("hasAuthority('ROLE_ADMIN')")
    public String adminProfile() {
        return "Welcome to Admin Profile";
    }

    @PostMapping("/generateToken")
    public String authenticateAndGetToken(@RequestBody AuthRequest authRequest) {
        Authentication authentication = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(authRequest.getEmail(), authRequest.getPassword()));
        System.out.println("HI");
        if (authentication.isAuthenticated()) {
            return jwtService.generateToken(authRequest.getEmail());
        } else {
            throw new UsernameNotFoundException("invalid user request !");
        }
    }

    @GetMapping("/getAllUsers")
    public List<UserInfo> getAllUsers() {
        System.out.println("list");
        return service.getAllUsers();
    }

    @GetMapping("/getUserEmail/{email}")
    public UserInfo getUserByEmail(@PathVariable String email) {
        return service.getByEmail(email);
    }

    @PutMapping("/update")
    public String updateUser(@RequestBody UserInfo userInfo) {
        return service.updateUser(userInfo);
    }

}
