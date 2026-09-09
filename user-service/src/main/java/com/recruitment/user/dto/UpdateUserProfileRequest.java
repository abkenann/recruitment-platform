package com.recruitment.user.dto;

import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UpdateUserProfileRequest {

    @Size(min = 2, max = 50, message = "firstName must be between 2 and 50 characters")
    private String firstName;

    @Size(min = 2, max = 50, message = "lastName must be between 2 and 50 characters")
    private String lastName;

    @Pattern(
            regexp = "^\\+?[0-9]{7,15}$",
            message = "phone format is invalid"
    )
    private String phone;

    @Size(max = 100, message = "city must not exceed 100 characters")
    private String city;

    @Size(max = 100, message = "country must not exceed 100 characters")
    private String country;

    @Size(max = 150, message = "headline must not exceed 150 characters")
    private String headline;

    @Size(max = 2000, message = "bio must not exceed 2000 characters")
    private String bio;
}