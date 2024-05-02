package club.esprit.backend.controllers;

import club.esprit.backend.dto.LoginRequest;
import club.esprit.backend.dto.LoginResponse;
import club.esprit.backend.entities.User;
import club.esprit.backend.services.jwt.UserServiceImpl;
import club.esprit.backend.utils.JwtUtil;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;

@RestController
@RequestMapping("/login")
public class LoginController {

    private final AuthenticationManager authenticationManager;

    private final UserServiceImpl customerService;
    private final UserServiceImpl userService;

    private final JwtUtil jwtUtil;


    @Autowired
    public LoginController(AuthenticationManager authenticationManager, UserServiceImpl customerService, UserServiceImpl userService, JwtUtil jwtUtil) {
        this.authenticationManager = authenticationManager;
        this.customerService = customerService;
        this.userService = userService;
        this.jwtUtil = jwtUtil;
    }

    @PostMapping
    public LoginResponse login(@RequestBody LoginRequest loginRequest, HttpServletResponse response) throws IOException {
        try {
            authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(loginRequest.getEmail(), loginRequest.getPassword()));
        } catch (BadCredentialsException e) {
            throw new BadCredentialsException("Incorrect email or password.");
        } catch (DisabledException disabledException) {
            response.sendError(HttpServletResponse.SC_NOT_FOUND, "User is not activated");
            return null;
        }
        final UserDetails userDetails = customerService.loadUserByUsername(loginRequest.getEmail());
        final String jwt = jwtUtil.generateToken(userDetails.getUsername());

        return new LoginResponse(jwt);
    }
    @GetMapping("/api/user-role")
    public ResponseEntity<String> getUserRole(@RequestHeader("Authorization") String authHeader) {
        // Extract token from Authorization header
        String token = authHeader.substring(7);

        // Extract username from token
        String username = jwtUtil.extractUsername(token);

        // Retrieve user's role based on username
        String role = userService.getUserRole(username);

        // Return user's role
        return ResponseEntity.ok(role);
    }
    @GetMapping("/api/current-user")
    public ResponseEntity<String> getCurrentUser(@RequestHeader("Authorization") String authHeader) {
        // Extract token from Authorization header
        String token = authHeader.substring(7);

        // Extract username from token
        String username = jwtUtil.extractUsername(token);

        // Retrieve user based on username
        User user = userService.getUserByUsername(username);

        // Return user's ID
        return ResponseEntity.ok(user.getName());
    }
}
