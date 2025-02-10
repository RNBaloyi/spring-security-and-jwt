package com.assessment.grad001.RixongoteloBaloyi.waste_sorting_api.controller;

import com.assessment.grad001.RixongoteloBaloyi.waste_sorting_api.dto.LoginRequest;
import com.assessment.grad001.RixongoteloBaloyi.waste_sorting_api.dto.LoginResponse;
import com.assessment.grad001.RixongoteloBaloyi.waste_sorting_api.exception.InvalidCredentialsException;
import com.assessment.grad001.RixongoteloBaloyi.waste_sorting_api.jwt.JwtUtils;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;

/**
 * Controller for handling authentication-related requests such as user login and JWT token generation.
 */
@RestController
@RequestMapping("/auth")
public class AuthController {

    private static final Logger logger = LoggerFactory.getLogger(AuthController.class);

    private final AuthenticationManager authenticationManager;
    private final JwtUtils jwtUtils;

    /**
     * Constructor to initialize AuthenticationManager and JwtUtils.
     *
     * @param authenticationManager the authentication manager for handling authentication logic
     * @param jwtUtils the utility class for JWT token generation
     */
    public AuthController(AuthenticationManager authenticationManager, JwtUtils jwtUtils) {
        this.authenticationManager = authenticationManager;
        this.jwtUtils = jwtUtils;
    }

    /**
     * Authenticates a user and generates a JWT token if the credentials are valid.
     *
     * @param loginRequest the login details (username, password)
     * @return ResponseEntity containing the login response with JWT token and user roles
     */
    @PostMapping("/login")
    public ResponseEntity<?> authenticateUser(@RequestBody LoginRequest loginRequest) {
        Authentication authentication;

        try {
            logger.info("Attempting to authenticate user: {}", loginRequest.getUsername());

            authentication = authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(loginRequest.getUsername(), loginRequest.getPassword()));

            logger.info("User {} authenticated successfully", loginRequest.getUsername());
        } catch (AuthenticationException exception) {
            logger.error("Authentication failed for user: {}", loginRequest.getUsername(), exception);
            throw new InvalidCredentialsException("Invalid username or password");
        }

        SecurityContextHolder.getContext().setAuthentication(authentication);
        UserDetails userDetails = (UserDetails) authentication.getPrincipal();

        String jwtToken = jwtUtils.generateTokenFromUsername(userDetails);

        List<String> roles = userDetails.getAuthorities().stream()
                .map(GrantedAuthority::getAuthority)
                .toList();

        LoginResponse response = new LoginResponse(userDetails.getUsername(), jwtToken, roles);

        logger.info("JWT Token generated for user: {}", userDetails.getUsername());

        return ResponseEntity.ok(response);
    }
}