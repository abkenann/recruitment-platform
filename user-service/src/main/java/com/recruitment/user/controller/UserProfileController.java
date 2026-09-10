package com.recruitment.user.controller;

import com.recruitment.user.dto.CreateUserProfileRequest;
import com.recruitment.user.dto.UpdateUserProfileRequest;
import com.recruitment.user.dto.UserProfileResponse;
import com.recruitment.user.entity.UserProfile;
import com.recruitment.user.security.AuthenticatedUser;
import com.recruitment.user.service.UserProfileService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/users")
@RequiredArgsConstructor
public class UserProfileController {

    private final UserProfileService userProfileService;

    @PostMapping
    public ResponseEntity<UserProfileResponse> createProfile(
            Authentication authentication,
            @Valid @RequestBody CreateUserProfileRequest request
    ) {
        AuthenticatedUser currentUser =
                (AuthenticatedUser) authentication.getPrincipal();

        UserProfile profile = userProfileService.createProfile(
                currentUser.getUserId(),
                currentUser.getEmail(),
                request
        );

        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(userProfileService.mapToResponse(profile));
    }

    @GetMapping("/{authUserId}")
    public ResponseEntity<UserProfileResponse> getProfile(
            @PathVariable Long authUserId
    ) {
        UserProfile profile =
                userProfileService.getProfileByAuthUserId(authUserId);

        return ResponseEntity.ok(
                userProfileService.mapToResponse(profile)
        );
    }

    @PutMapping("/{authUserId}")
    public ResponseEntity<UserProfileResponse> updateProfile(
            @PathVariable Long authUserId,
            @Valid @RequestBody UpdateUserProfileRequest request
    ) {
        UserProfile profile =
                userProfileService.updateProfile(authUserId, request);

        return ResponseEntity.ok(
                userProfileService.mapToResponse(profile)
        );
    }

    @DeleteMapping("/{authUserId}")
    public ResponseEntity<Void> deleteProfile(
            @PathVariable Long authUserId
    ) {
        userProfileService.deleteProfile(authUserId);

        return ResponseEntity.noContent().build();
    }

    @GetMapping("/me")
    public ResponseEntity<UserProfileResponse> getMyProfile(
            Authentication authentication
    ) {
        AuthenticatedUser currentUser =
                (AuthenticatedUser) authentication.getPrincipal();

        UserProfile profile =
                userProfileService.getProfileByAuthUserId(
                        currentUser.getUserId()
                );

        return ResponseEntity.ok(
                userProfileService.mapToResponse(profile)
        );
    }

    @PatchMapping("/me")
    public ResponseEntity<UserProfileResponse> updateMyProfile(
            Authentication authentication,
            @Valid @RequestBody UpdateUserProfileRequest request
    ) {
        AuthenticatedUser currentUser =
                (AuthenticatedUser) authentication.getPrincipal();

        UserProfile profile =
                userProfileService.updateProfile(
                        currentUser.getUserId(),
                        request
                );

        return ResponseEntity.ok(
                userProfileService.mapToResponse(profile)
        );
    }
}