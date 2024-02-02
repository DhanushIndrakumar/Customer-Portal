package customer.company.controllers;

import customer.company.dto.AuthRequest;
import customer.company.dto.AuthResponse;
import customer.company.dto.UserDTO;
import customer.company.entities.User;
import customer.company.service.UserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@Tag(name="Customer")
public class UserController {
    @Autowired
    private UserService userService;


    @Operation(
            description="Get Endpoint to display all the customers",
            summary="All Customer details will be displayed "
    )
    @GetMapping
    public List<UserDTO> getAllUsers() {
        return userService.getAllUsers();
    }

    @Operation(
            description="Post Endpoint to create a Customer",
            summary="A customer can be created using this endpoint "
    )

    @PostMapping("auth/users/register")
   // @ApiOperation("Creation of User")
    public UserDTO createUser(@RequestBody User user) {
        return userService.createUser(user);
    }


    @Operation(
            description="Post Endpoint to login",
            summary="Customer Login "
    )

    @PostMapping("auth/users/login")
   // @ApiOperation("Performing Login operation by providing email and password")
    public AuthResponse login(@RequestBody AuthRequest authRequest) {
        return userService.login(authRequest);
    }

    @Operation(
            description="Get Endpoint to retrieve a particular customer",
            summary="A particular customer details can be extracted by providing ID "
    )
    @GetMapping("users/{userID}")
    //@ApiOperation("Retrieving a particular based on UserID ")
    public UserDTO getUserById(@PathVariable Long userID) {
        return userService.getUserById(userID);
    }

    @Operation(
            description="Put Endpoint to edit a particular customer details",
            summary="A particular customer details can be updated by providing ID "
    )
    @PutMapping("users/{userID}")
   // @ApiOperation("Performing Update Operation")
    public UserDTO updateUser(@PathVariable Long userID, @RequestBody UserDTO userDTO) {
        return userService.updateUser(userID, userDTO);
    }

    @Operation(
            description="Delete Endpoint to remove a particular customer ",
            summary="A particular customer can be removed by providing ID "
    )
    @DeleteMapping("users/{userID}")
    //@ApiOperation("Deleting a user provided the UserID")
    public void deleteUser(@PathVariable Long userID) {
        userService.deleteUser(userID);
    }

}
