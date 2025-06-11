package com.library.management.service;

import com.library.management.model.User;
import com.library.management.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Service
public class AuthService {

    private static final Logger logger = LoggerFactory.getLogger(AuthService.class);

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    public User authenticate(String username, String password) {
        logger.info("Attempting authentication for username: {}", username);

        User user = userRepository.findByUsername(username)
                .orElseThrow(() -> {
                    logger.warn("Authentication failed: user '{}' not found", username);
                    return new RuntimeException("Invalid username or password");
                });

        logger.debug("User found: {}, checking password", user.getUsername());

        if (!passwordEncoder.matches(password, user.getPassword())) {
            logger.warn("Authentication failed: password mismatch for user '{}'", username);
            throw new RuntimeException("Invalid username or password");
        }

        logger.info("Authentication successful for user: {}", username);
        return user;
    }

    public User register(User user) throws Exception {
        logger.info("Registering new user: {}", user.getUsername());

        if (userRepository.findByUsername(user.getUsername()).isPresent()) {
            logger.warn("Registration failed: username '{}' already exists", user.getUsername());
            throw new Exception("Username already exists");
        }

        user.setPassword(passwordEncoder.encode(user.getPassword()));
        User savedUser = userRepository.save(user);

        logger.info("User '{}' registered successfully", savedUser.getUsername());
        return savedUser;
    }
}
