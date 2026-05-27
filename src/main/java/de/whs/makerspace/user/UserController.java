package de.whs.makerspace.user;

import de.whs.makerspace.user.dto.UserRequest;
import de.whs.makerspace.user.dto.UserResponse;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/users")
public class UserController {

    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping
    public List<UserResponse> getAllUsers() {
        return userService.getAllUsers();
    }

    @GetMapping("/{id}")
    public UserResponse getUserById(@PathVariable("id") Long id) {
        return userService.getUserById(id);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public UserResponse createUser(@Valid @RequestBody UserRequest request) {
        return userService.createUser(request);
    }

    @PutMapping("/{id}")
    public UserResponse updateUser(
            @PathVariable("id") Long id,
            @Valid @RequestBody UserRequest request
    ) {
        return userService.updateUser(id, request);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteUser(@PathVariable("id") Long id) {
        userService.deleteUser(id);
    }
}