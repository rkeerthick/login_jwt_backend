package com.example.login_jwt;

import com.example.login_jwt.entity.UserInfo;
import com.example.login_jwt.repository.UserInfoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@SpringBootApplication
public class LoginJwtApplication {



	public static void main(String[] args) {
		SpringApplication.run(LoginJwtApplication.class, args);
	}

}
