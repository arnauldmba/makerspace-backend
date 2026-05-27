package de.whs.makerspace.user;

import de.whs.makerspace.common.exception.ResourceNotFoundException;
import de.whs.makerspace.user.dto.UserRequest;
import de.whs.makerspace.user.dto.UserResponse;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserService {

    private final UserRepository userRepository;

    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public List<UserResponse> getAllUsers() {
        return userRepository.findAll()
                .stream()
                .map(UserMapper::toResponse)
                .toList();
    }

    public UserResponse getUserById(Long id) {
        User user = findUserById(id);
        return UserMapper.toResponse(user);
    }

    public UserResponse createUser(UserRequest request) {
        if (userRepository.existsByEmail(request.email())) {
            throw new IllegalArgumentException("Email already exists: " + request.email());
        }

        User user = UserMapper.toEntity(request);
        User savedUser = userRepository.save(user);

        return UserMapper.toResponse(savedUser);
    }

    public UserResponse updateUser(Long id, UserRequest request) {
        User user = findUserById(id);

        if (!user.getEmail().equals(request.email()) && userRepository.existsByEmail(request.email())) {
            throw new IllegalArgumentException("Email already exists: " + request.email());
        }

        UserMapper.updateEntity(user, request);
        User updatedUser = userRepository.save(user);

        return UserMapper.toResponse(updatedUser);
    }

    public void deleteUser(Long id) {
        User user = findUserById(id);
        userRepository.delete(user);
    }

    public User findUserById(Long id) {
        return userRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("User not found with id: " + id));
    }
}