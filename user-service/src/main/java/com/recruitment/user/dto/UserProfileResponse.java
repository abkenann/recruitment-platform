package com.recruitment.user.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
@AllArgsConstructor
public class UserProfileResponse {

    private Long id;
    private Long authUserId;
    private String firstName;
    private String lastName;
    private String email;
    private String phone;
    private String city;
    private String country;
    private String headline;
    private String bio;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
}