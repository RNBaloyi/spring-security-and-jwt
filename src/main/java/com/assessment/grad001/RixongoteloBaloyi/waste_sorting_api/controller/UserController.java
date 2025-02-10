package com.assessment.grad001.RixongoteloBaloyi.waste_sorting_api.controller;

import com.assessment.grad001.RixongoteloBaloyi.waste_sorting_api.dto.RegisterRequest;
import com.assessment.grad001.RixongoteloBaloyi.waste_sorting_api.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;


/**
 * Controller for handling user-related requests including registration,
 * role management, retrieving, updating, and deleting users.
 */
@RestController
@RequestMapping("/api/v1/users")
public class UserController {

    private final UserService userService;

    @Autowired
    public UserController(UserService userService) {
        this.userService = userService;
    }

    /**
     * Registers a new user with the 'USER' role.
     *
     * @param registerRequest the user registration details (username, password)
     * @return ResponseEntity indicating the result of the registration process
     */
    @PostMapping("/register")
    public ResponseEntity<?> registerUser(@RequestBody RegisterRequest registerRequest) {
        return userService.registerUser(registerRequest); // Directly return the ResponseEntity from service
    }

    /**
     * Adds a role to an existing user.
     *
     * @param username the username of the user to whom the role will be added
     * @param roleName the role name (e.g., "ROLE_ADMIN", "ROLE_USER")
     * @return ResponseEntity indicating the result of the role addition process
     */
    @PreAuthorize("hasRole('ADMIN')")
    @PostMapping("/add-role/{username}")
    public ResponseEntity<?> addRoleToUser(@PathVariable String username, @RequestBody String roleName) {
        return userService.addRoleToUser(username, roleName.trim()); // Directly return the ResponseEntity from service
    }

    /**
     * Retrieves all registered users.
     *
     * @return ResponseEntity containing a list of all users
     */
    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping
    public ResponseEntity<?> getAllUsers() {
        return userService.getAllUsers();
    }

    /**
     * Retrieves a user by their username.
     *
     * @param username the username of the user to retrieve
     * @return ResponseEntity containing the user details
     */
    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping("/{username}")
    public ResponseEntity<?> getUserByUsername(@PathVariable String username) {
        return userService.getUserByUsername(username);
    }

    /**
     * Updates the details of an existing user.
     *
     * @param username the username of the user to be updated
     * @param registerRequest the updated user details (username, password)
     * @return ResponseEntity indicating the result of the update process
     */
    @PreAuthorize("hasRole('ADMIN')")
    @PutMapping("/{username}")
    public ResponseEntity<?> updateUser(@PathVariable String username, @RequestBody RegisterRequest registerRequest) {
        return userService.updateUser(username, registerRequest);
    }

    /**
     * Deletes a user by their username.
     *
     * @param username the username of the user to be deleted
     * @return ResponseEntity indicating the result of the deletion process
     */
    @PreAuthorize("hasRole('ADMIN')")
    @DeleteMapping("/{username}")
    public ResponseEntity<?> deleteUser(@PathVariable String username) {
        return userService.deleteUser(username);
    }
}
