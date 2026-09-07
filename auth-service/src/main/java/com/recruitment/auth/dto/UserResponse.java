package com.recruitment.auth.dto;

import com.recruitment.auth.entity.Role;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
@AllArgsConstructor
public class UserResponse {

    private Long id;
    private String firstName;
    private String lastName;
    private String email;
    private Role role;
    private Boolean enabled;
    private LocalDateTime createdAt;
}