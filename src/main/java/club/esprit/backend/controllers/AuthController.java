package club.esprit.backend.controllers;

import club.esprit.backend.dto.LoginRequest;
import club.esprit.backend.dto.LoginResponse;
import club.esprit.backend.dto.SignupRequest;
import club.esprit.backend.entities.User;
import club.esprit.backend.repository.UserRepository;
import club.esprit.backend.services.IAuth;
import club.esprit.backend.services.jwt.UserServiceImpl;
import club.esprit.backend.utils.JwtUtil;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;

@RestController
@RequestMapping("/auth")
public class AuthController {

    private final IAuth IAuth;
    private final AuthenticationManager authenticationManager;
    private final UserServiceImpl customerService;
    private final JwtUtil jwtUtil;
    private final UserRepository userRepository;


    @Autowired
    public AuthController(IAuth IAuth, AuthenticationManager authenticationManager,
                          UserServiceImpl customerService, JwtUtil jwtUtil, UserRepository userRepository) {
        this.IAuth = IAuth;
        this.authenticationManager = authenticationManager;
        this.customerService = customerService;
        this.jwtUtil = jwtUtil;
        this.userRepository = userRepository;
    }

    @PostMapping("/signup")
    public ResponseEntity<?> signupUser(@RequestBody SignupRequest signupRequest) {
        try {
            User createdUser = IAuth.createUser(signupRequest);
            return ResponseEntity.status(HttpStatus.CREATED).body(createdUser);
        } catch (RuntimeException e) {
            if (e.getMessage().equals("Email already exists")) {
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Email already exists");
            } else {
                return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("An error occurred");
            }
        }
    }

    @PostMapping("/login")
    public LoginResponse login(@RequestBody LoginRequest loginRequest, HttpServletResponse response) throws IOException {
        try {
            // Authenticate user
            authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(loginRequest.getEmail(), loginRequest.getPassword()));

            // Check if the user is active
            UserDetails userDetails = customerService.loadUserByUsername(loginRequest.getEmail());
            User user = userRepository.findByEmail(loginRequest.getEmail())
                    .orElseThrow(() -> new UsernameNotFoundException("User not found with email: " + loginRequest.getEmail()));

            if (!user.isActive()) {
                response.sendError(HttpServletResponse.SC_FORBIDDEN, "User is not activated");
                return null;
            }

            // Generate JWT token
            String jwt = jwtUtil.generateToken(userDetails.getUsername());
            String refreshToken = jwtUtil.generateRefreshToken(userDetails.getUsername());

            return new LoginResponse(jwt, refreshToken);
        } catch (BadCredentialsException e) {
            throw new BadCredentialsException("Incorrect email or password.");
        }
    }





}
