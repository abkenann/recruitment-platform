package com.recruitment.auth.service;

import com.recruitment.auth.entity.RefreshToken;
import com.recruitment.auth.entity.User;
import com.recruitment.auth.exception.InvalidCredentialsException;
import com.recruitment.auth.repository.RefreshTokenRepository;
import com.recruitment.auth.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class RefreshTokenService {

    private final RefreshTokenRepository refreshTokenRepository;
    private final UserRepository userRepository;

    @Value("${jwt.refresh-expiration}")
    private long refreshExpiration;

    public RefreshToken createRefreshToken(User user) {

        RefreshToken refreshToken = RefreshToken.builder()
                .token(UUID.randomUUID().toString())
                .userId(user.getId())
                .expiresAt(
                        LocalDateTime.now()
                                .plusSeconds(refreshExpiration / 1000)
                )
                .revoked(false)
                .build();

        return refreshTokenRepository.save(refreshToken);
    }

    public User validateRefreshToken(String token) {

        RefreshToken refreshToken = refreshTokenRepository
                .findByToken(token)
                .orElseThrow(() ->
                        new InvalidCredentialsException("Invalid refresh token")
                );

        if (refreshToken.getRevoked()) {
            throw new InvalidCredentialsException("Refresh token has been revoked");
        }

        if (refreshToken.getExpiresAt().isBefore(LocalDateTime.now())) {
            throw new InvalidCredentialsException("Refresh token has expired");
        }

        return userRepository.findById(refreshToken.getUserId())
                .orElseThrow(() ->
                        new InvalidCredentialsException("User not found")
                );
    }

    public void revokeRefreshToken(String token) {

        RefreshToken refreshToken = refreshTokenRepository
                .findByToken(token)
                .orElseThrow(() ->
                        new InvalidCredentialsException("Invalid refresh token")
                );

        refreshToken.setRevoked(true);

        refreshTokenRepository.save(refreshToken);
    }
}