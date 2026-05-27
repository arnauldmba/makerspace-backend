package de.whs.makerspace.user;

import de.whs.makerspace.user.dto.UserRequest;
import de.whs.makerspace.user.dto.UserResponse;

public class UserMapper {

    public static UserResponse toResponse(User user) {
        return new UserResponse(
                user.getId(),
                user.getFirstName(),
                user.getLastName(),
                user.getEmail(),
                user.getRole()
        );
    }

    public static User toEntity(UserRequest request) {
        User user = new User();
        user.setFirstName(request.firstName());
        user.setLastName(request.lastName());
        user.setEmail(request.email());
        user.setPassword(request.password());
        user.setRole(request.role());
        return user;
    }

    public static void updateEntity(User user, UserRequest request) {
        user.setFirstName(request.firstName());
        user.setLastName(request.lastName());
        user.setEmail(request.email());
        user.setPassword(request.password());
        user.setRole(request.role());
    }
}