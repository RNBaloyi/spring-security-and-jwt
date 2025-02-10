package com.assessment.grad001.RixongoteloBaloyi.waste_sorting_api.service;
import com.assessment.grad001.RixongoteloBaloyi.waste_sorting_api.dto.RegisterRequest;
import com.assessment.grad001.RixongoteloBaloyi.waste_sorting_api.exception.ResourceNotFoundException;
import com.assessment.grad001.RixongoteloBaloyi.waste_sorting_api.model.Role;
import com.assessment.grad001.RixongoteloBaloyi.waste_sorting_api.model.User;
import com.assessment.grad001.RixongoteloBaloyi.waste_sorting_api.repo.RoleRepository;
import com.assessment.grad001.RixongoteloBaloyi.waste_sorting_api.repo.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final RoleRepository roleRepository;
    private final PasswordEncoder passwordEncoder;

    @Autowired
    public UserServiceImpl(UserRepository userRepository, RoleRepository roleRepository, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.roleRepository = roleRepository;
        this.passwordEncoder = passwordEncoder;
    }

    /**
     * Registers a new user with the 'USER' role.
     *
     * @param registerRequest the user registration details (username, password)
     * @return ResponseEntity indicating the result of the registration
     */
    @Override
    public ResponseEntity<?> registerUser(RegisterRequest registerRequest) {
        if (userRepository.existsByUsername(registerRequest.getUsername())) {
            return ResponseEntity.badRequest().body("Username is already taken.");
        }

        Role userRole = roleRepository.findByName("ROLE_USER")
                .orElseThrow(() -> new ResourceNotFoundException("ROLE_USER not found"));

        User newUser = new User(registerRequest.getUsername(),
                passwordEncoder.encode(registerRequest.getPassword()));
        newUser.getRoles().add(userRole);

        userRepository.save(newUser);

        return ResponseEntity.ok("User registered successfully.");
    }

    /**
     * Adds a new role to an existing user.
     *
     * @param username the username of the user to whom the role will be added
     * @param roleName the role name (e.g., "ROLE_ADMIN", "ROLE_USER")
     * @return ResponseEntity indicating the result of the role addition
     */
    @Override
    public ResponseEntity<?> addRoleToUser(String username, String roleName) {
        User user = userRepository.findByUsername(username)
                .orElseThrow(() -> new ResourceNotFoundException("User not found"));

        Role role = roleRepository.findByName(roleName)
                .orElseThrow(() -> new ResourceNotFoundException("Role not found"));

        user.getRoles().add(role);
        userRepository.save(user);

        return ResponseEntity.ok("Role added successfully.");
    }

    /**
     * Retrieves all users in the system.
     *
     * @return ResponseEntity containing a list of all users
     */
    @Override
    public ResponseEntity<?> getAllUsers() {
        return ResponseEntity.ok(userRepository.findAll());
    }

    /**
     * Retrieves a user by their username.
     *
     * @param username the username of the user to retrieve
     * @return ResponseEntity containing the user details
     */
    @Override
    public ResponseEntity<?> getUserByUsername(String username) {
        User user = userRepository.findByUsername(username)
                .orElseThrow(() -> new ResourceNotFoundException("User not found"));

        return ResponseEntity.ok(user);
    }

    /**
     * Updates an existing user's information (e.g., username, password, roles).
     *
     * @param username the username of the user to update
     * @param registerRequest the updated user details
     * @return ResponseEntity indicating the result of the update
     */
    @Override
    public ResponseEntity<?> updateUser(String username, RegisterRequest registerRequest) {
        User user = userRepository.findByUsername(username)
                .orElseThrow(() -> new ResourceNotFoundException("User not found"));

        user.setUsername(registerRequest.getUsername());
        user.setPassword(passwordEncoder.encode(registerRequest.getPassword()));

        userRepository.save(user);

        return ResponseEntity.ok("User updated successfully.");
    }

    /**
     * Deletes a user by their username.
     *
     * @param username the username of the user to delete
     * @return ResponseEntity indicating the result of the deletion
     */
    @Override
    public ResponseEntity<?> deleteUser(String username) {
        User user = userRepository.findByUsername(username)
                .orElseThrow(() -> new ResourceNotFoundException("User not found"));

        userRepository.delete(user);

        return ResponseEntity.ok("User deleted successfully.");
    }
}
