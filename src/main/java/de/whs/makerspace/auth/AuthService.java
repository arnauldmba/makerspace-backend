package de.whs.makerspace.auth;

import de.whs.makerspace.auth.dto.*;
import de.whs.makerspace.common.exception.ResourceNotFoundException;
import de.whs.makerspace.security.JwtService;
import de.whs.makerspace.user.*;
import de.whs.makerspace.user.dto.UserResponse;

import lombok.RequiredArgsConstructor;

import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;

    public AuthResponse register(RegisterRequest request) {

        if (userRepository.existsByEmail(request.email())) {
            throw new IllegalArgumentException("Email already exists");
        }

        User user = User.builder()
                .firstName(request.firstName())
                .lastName(request.lastName())
                .email(request.email())
                .password(passwordEncoder.encode(request.password()))
                .role(UserRole.STUDENT)
                .build();

        userRepository.save(user);

        String token = jwtService.generateToken(user);

        return new AuthResponse(token);
    }

    public AuthResponse login(LoginRequest request) {

        User user = userRepository.findByEmail(request.email())
                .orElseThrow(() ->
                        new IllegalArgumentException("Invalid credentials")
                );

        String token = jwtService.generateToken(user);

        boolean passwordMatches = passwordEncoder.matches(
                request.password(),
                user.getPassword()
        );

        System.out.println(request.password());
        System.out.println(user.getPassword());

        if (!passwordMatches) {
            throw new IllegalArgumentException("Invalid credentials");
        }


System.out.println(passwordMatches);

        return new AuthResponse(token);
    }

    public UserResponse getCurrentUser() {

        String email = SecurityContextHolder
                .getContext()
                .getAuthentication()
                .getName();

        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new ResourceNotFoundException("User not found"));

        return UserMapper.toResponse(user);
    }
}