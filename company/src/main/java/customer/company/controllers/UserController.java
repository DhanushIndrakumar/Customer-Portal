package customer.company.controllers;

import customer.company.dto.AuthRequest;
import customer.company.dto.AuthResponse;
import customer.company.dto.UserDTO;
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
    //@ApiOperation("Returns all Users")
    public List<UserDTO> getAllUsers() {
        return userService.getAllUsers();
    }


    @PostMapping("auth/users/register")
   // @ApiOperation("Creation of User")
    public UserDTO createUser(@RequestBody User user) {
        return userService.createUser(user);
    }

    @PostMapping("auth/users/login")
   // @ApiOperation("Performing Login operation by providing email and password")
    public AuthResponse login(@RequestBody AuthRequest authRequest) {
        return userService.login(authRequest);
    }

    @GetMapping("users/{userID}")
    //@ApiOperation("Retrieving a particular based on UserID ")
    public UserDTO getUserById(@PathVariable Long userID) {
        return userService.getUserById(userID);
    }

    @PutMapping("users/{userID}")
   // @ApiOperation("Performing Update Operation")
    public UserDTO updateUser(@PathVariable Long userID, @RequestBody UserDTO userDTO) {
        return userService.updateUser(userID, userDTO);
    }
    @DeleteMapping("users/{userID}")
    //@ApiOperation("Deleting a user provided the UserID")
    public void deleteUser(@PathVariable Long userID) {
        userService.deleteUser(userID);
    }

}
