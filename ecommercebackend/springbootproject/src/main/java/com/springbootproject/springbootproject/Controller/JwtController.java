package com.springbootproject.springbootproject.Controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.web.bind.annotation.*;

import com.springbootproject.springbootproject.Jwt.JwtHelper;
import com.springbootproject.springbootproject.JwtUtils.JwtRequest;
import com.springbootproject.springbootproject.JwtUtils.JwtResponse;

/**
 * Controller class for handling JWT authentication.
 */
@RestController
@CrossOrigin
public class JwtController {

    private static final Logger log = LoggerFactory.getLogger(JwtController.class);

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private JwtHelper jwtHelper;

    @Autowired
    private UserDetailsService userDetailsService;

    /**
     * Authenticates the user and generates a JWT token.
     *
     * @param authenticationRequest the JWT authentication request containing
     *                              username and password.
     * @return ResponseEntity containing the JWT response with the generated token.
     * @throws Exception if authentication fails.
     */
    @PostMapping("/authenticate")
    public ResponseEntity<JwtResponse> createAuthenticationToken(@RequestBody JwtRequest authenticationRequest)
            throws Exception {
        log.info("Received authentication request for user: {}", authenticationRequest.getUserName());

        // Authenticate the user with provided credentials
        authenticate(authenticationRequest.getUserName(), authenticationRequest.getUserPassword());

        // Load user details from the database or other source
        final UserDetails userDetails = userDetailsService.loadUserByUsername(authenticationRequest.getUserName());

        // Generate JWT token based on user details
        final String token = jwtHelper.doGenerateToken(userDetails);

        log.info("Generated JWT token for user: {}", userDetails.getUsername());

        // Return response entity containing the generated JWT token
        return ResponseEntity.ok(new JwtResponse(userDetails.getUsername(), token));
    }

    /**
     * Authenticates the user using the provided username and password.
     *
     * @param username the username of the user.
     * @param password the password of the user.
     * @throws Exception if authentication fails.
     */
    private void authenticate(String username, String password) throws Exception {
        try {
            log.info("Authenticating user: {}", username);

            // Perform authentication 
            authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(username, password));

            log.info("User authenticated successfully: {}", username);
        } catch (BadCredentialsException e) {
            log.error("Authentication failed for user: {}", username);
            //if authentication fails then throw exception
            throw new Exception("INVALID_CREDENTIALS");
        }
    }
}