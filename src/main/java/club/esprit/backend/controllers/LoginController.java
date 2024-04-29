//package club.esprit.backend.controllers;
//
//import club.esprit.backend.dto.LoginRequest;
//import club.esprit.backend.dto.LoginResponse;
//import club.esprit.backend.entities.User;
//import club.esprit.backend.repository.UserRepository;
//import club.esprit.backend.services.jwt.UserServiceImpl;
//import club.esprit.backend.utils.JwtUtil;
//import jakarta.servlet.http.HttpServletResponse;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.security.authentication.AuthenticationManager;
//import org.springframework.security.authentication.BadCredentialsException;
//import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
//import org.springframework.security.core.userdetails.UserDetails;
//import org.springframework.security.core.userdetails.UsernameNotFoundException;
//import org.springframework.web.bind.annotation.PostMapping;
//import org.springframework.web.bind.annotation.RequestBody;
//import org.springframework.web.bind.annotation.RequestMapping;
//import org.springframework.web.bind.annotation.RestController;
//
//import java.io.IOException;
//
//@RestController
//@RequestMapping("/login")
//public class LoginController {
//
//    private final AuthenticationManager authenticationManager;
//    private final UserServiceImpl userService;
//    private final JwtUtil jwtUtil;
//    private final UserRepository userRepository;
//
//
//
//    @Autowired
//    public LoginController(AuthenticationManager authenticationManager,
//                           UserServiceImpl userService, JwtUtil jwtUtil, UserRepository userRepository) {
//        this.authenticationManager = authenticationManager;
//        this.userService = userService;
//        this.jwtUtil = jwtUtil;
//        this.userRepository = userRepository;
//    }
//
//    @PostMapping
//    public LoginResponse login(@RequestBody LoginRequest loginRequest, HttpServletResponse response) throws IOException {
//        try {
//            // Authenticate user
//            authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(loginRequest.getEmail(), loginRequest.getPassword()));
//
//            // Check if the user is active
//            UserDetails userDetails = userService.loadUserByUsername(loginRequest.getEmail());
//            User user = userRepository.findByEmail(loginRequest.getEmail())
//                    .orElseThrow(() -> new UsernameNotFoundException("User not found with email: " + loginRequest.getEmail()));
//
//            if (!user.isActive()) {
//                response.sendError(HttpServletResponse.SC_FORBIDDEN, "User is not activated");
//                return null;
//            }
//
//            // Generate JWT token
//            String jwt = jwtUtil.generateToken(userDetails.getUsername());
//            String refreshToken = jwtUtil.generateRefreshToken(userDetails.getUsername());
//
//            return new LoginResponse(jwt, refreshToken);
//        } catch (BadCredentialsException e) {
//            throw new BadCredentialsException("Incorrect email or password.");
//        }
//    }
//
//
//}
