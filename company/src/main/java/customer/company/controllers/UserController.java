package customer.company.controllers;

import customer.company.dto.AuthRequest;
import customer.company.dto.AuthResponse;
import customer.company.dto.UserResponse;
import customer.company.entities.User;
import customer.company.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController


public class UserController {
    @Autowired
    private UserService userService;

    @GetMapping
    public List<User> getAllUsers() {
        return userService.getAllUsers();
    }


    @PostMapping("auth/users/register")
    public UserResponse createUser(@RequestBody User user) {
        return userService.createUser(user);
    }

    @PostMapping("auth/users/login")
    public AuthResponse login(@RequestBody AuthRequest authRequest) {
        return userService.login(authRequest);
    }

    @GetMapping("users/{userID}")
    public User getUserById(@PathVariable Long userID) {
        return userService.getUserById(userID);
    }

    @PutMapping("users/{userID}")
    public User updateUser(@PathVariable Long userID, @RequestBody User user) {
        return userService.updateUser(userID, user);
    }

    @DeleteMapping("users/{userID}")
    public void deleteUser(@PathVariable Long userID) {
        userService.deleteUser(userID);
    }
}
