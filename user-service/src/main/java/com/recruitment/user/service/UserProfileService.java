package com.recruitment.user.service;

import com.recruitment.user.dto.CreateUserProfileRequest;
import com.recruitment.user.dto.UpdateUserProfileRequest;
import com.recruitment.user.dto.UserProfileResponse;
import com.recruitment.user.entity.UserProfile;
import com.recruitment.user.exception.UserProfileAlreadyExistsException;
import com.recruitment.user.exception.UserProfileNotFoundException;
import com.recruitment.user.repository.UserProfileRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;


@Service
@RequiredArgsConstructor
public class UserProfileService {

    private final UserProfileRepository userProfileRepository;

    public UserProfile createProfile(CreateUserProfileRequest request) {

        if (userProfileRepository.existsByAuthUserId(request.getAuthUserId())) {
            throw new UserProfileAlreadyExistsException(
                    "User profile already exists for this authUserId"
            );
        }

        if (userProfileRepository.existsByEmail(request.getEmail())) {
            throw new UserProfileAlreadyExistsException(
                    "User profile already exists for this email"
            );
        }

        UserProfile profile = UserProfile.builder()
                .authUserId(request.getAuthUserId())
                .firstName(request.getFirstName())
                .lastName(request.getLastName())
                .email(request.getEmail())
                .phone(request.getPhone())
                .city(request.getCity())
                .country(request.getCountry())
                .headline(request.getHeadline())
                .bio(request.getBio())
                .build();

        return userProfileRepository.save(profile);
    }

    public UserProfile getProfileByAuthUserId(Long authUserId) {
        return userProfileRepository.findByAuthUserId(authUserId)
                .orElseThrow(() ->
                        new UserProfileNotFoundException("User profile not found")
                );
    }

    public UserProfile updateProfile(
            Long authUserId,
            UpdateUserProfileRequest request
    ) {
        UserProfile profile = userProfileRepository
                .findByAuthUserId(authUserId)
                .orElseThrow(() ->
                        new UserProfileNotFoundException("User profile not found")
                );

        if (request.getFirstName() != null) {
            profile.setFirstName(request.getFirstName());
        }

        if (request.getLastName() != null) {
            profile.setLastName(request.getLastName());
        }

        if (request.getPhone() != null) {
            profile.setPhone(request.getPhone());
        }

        if (request.getCity() != null) {
            profile.setCity(request.getCity());
        }

        if (request.getCountry() != null) {
            profile.setCountry(request.getCountry());
        }

        if (request.getHeadline() != null) {
            profile.setHeadline(request.getHeadline());
        }

        if (request.getBio() != null) {
            profile.setBio(request.getBio());
        }

        return userProfileRepository.save(profile);
    }

    public void deleteProfile(Long authUserId) {
        UserProfile profile = userProfileRepository
                .findByAuthUserId(authUserId)
                .orElseThrow(() ->
                        new UserProfileNotFoundException("User profile not found")
                );

        userProfileRepository.delete(profile);
    }

    public UserProfileResponse mapToResponse(UserProfile profile) {
        return new UserProfileResponse(
                profile.getId(),
                profile.getAuthUserId(),
                profile.getFirstName(),
                profile.getLastName(),
                profile.getEmail(),
                profile.getPhone(),
                profile.getCity(),
                profile.getCountry(),
                profile.getHeadline(),
                profile.getBio(),
                profile.getCreatedAt(),
                profile.getUpdatedAt()
        );
    }
}