package com.example.login_jwt.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.*;

@Data
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "userInfo")
public class UserInfo {
    @Id
    private int id;
    private String firstName;
    private String lastName;
    private String dob;
    private String phoneNumber;
    private String password;
    private String email;
}
