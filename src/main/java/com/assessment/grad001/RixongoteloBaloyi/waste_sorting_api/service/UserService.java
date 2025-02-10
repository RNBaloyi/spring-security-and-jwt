package com.assessment.grad001.RixongoteloBaloyi.waste_sorting_api.service;

import com.assessment.grad001.RixongoteloBaloyi.waste_sorting_api.dto.RegisterRequest;
import org.springframework.http.ResponseEntity;

public interface UserService {

    /**
     * Registers a new user with the 'USER' role.
     *
     * @param registerRequest the user registration details (username, password)
     * @return ResponseEntity indicating the result of the registration
     */
    ResponseEntity<?> registerUser(RegisterRequest registerRequest);

    /**
     * Adds a new role to an existing user.
     *
     * @param username the username of the user to whom the role will be added
     * @param roleName the role name (e.g., "ROLE_ADMIN", "ROLE_USER")
     * @return ResponseEntity indicating the result of the role addition
     */
    ResponseEntity<?> addRoleToUser(String username, String roleName);

    /**
     * Retrieves all users in the system.
     *
     * @return ResponseEntity containing a list of all users
     */
    ResponseEntity<?> getAllUsers();

    /**
     * Retrieves a user by their username.
     *
     * @param username the username of the user to retrieve
     * @return ResponseEntity containing the user details
     */
    ResponseEntity<?> getUserByUsername(String username);

    /**
     * Updates an existing user's information (e.g., username, password, roles).
     *
     * @param username the username of the user to update
     * @param registerRequest the updated user details
     * @return ResponseEntity indicating the result of the update
     */
    ResponseEntity<?> updateUser(String username, RegisterRequest registerRequest);

    /**
     * Deletes a user by their username.
     *
     * @param username the username of the user to delete
     * @return ResponseEntity indicating the result of the deletion
     */
    ResponseEntity<?> deleteUser(String username);
}
