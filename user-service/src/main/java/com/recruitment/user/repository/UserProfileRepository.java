package com.recruitment.user.repository;

import com.recruitment.user.entity.UserProfile;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserProfileRepository extends JpaRepository<UserProfile, Long> {

    Optional<UserProfile> findByAuthUserId(Long authUserId);

    Optional<UserProfile> findByEmail(String email);

    boolean existsByAuthUserId(Long authUserId);

    boolean existsByEmail(String email);
}