package customer.company.controllers;

import customer.company.dto.AuthRequest;
import customer.company.dto.AuthResponse;
import customer.company.entities.User;
import customer.company.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController


public class UserController {
    @Autowired
    private UserService userService;


    @PostMapping("auth/users/register")
    public User createUser(@RequestBody User user) {
        return userService.createUser(user);
    }
    @PostMapping("auth/users/login")
    public AuthResponse login(@RequestBody AuthRequest authRequest) {
        return userService.login(authRequest);
    }
}
