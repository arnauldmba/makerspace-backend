package de.whs.makerspace.user.dto;

import de.whs.makerspace.user.UserRole;

public record UserResponse(
        Long id,
        String firstName,
        String lastName,
        String email,
        UserRole role
) {
}