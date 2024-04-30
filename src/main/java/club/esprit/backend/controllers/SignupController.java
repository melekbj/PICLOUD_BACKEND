
//package club.esprit.backend.controllers;
//
//import club.esprit.backend.dto.SignupRequest;
//import club.esprit.backend.entities.User;
//import club.esprit.backend.services.AuthService;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.http.HttpStatus;
//import org.springframework.http.ResponseEntity;
//import org.springframework.web.bind.annotation.PostMapping;
//import org.springframework.web.bind.annotation.RequestBody;
//import org.springframework.web.bind.annotation.RequestMapping;
//import org.springframework.web.bind.annotation.RestController;
//
//@RestController
//@RequestMapping("/signup")
//public class SignupController {
//
//    private final AuthService authService;
//
//    @Autowired
//    public SignupController(AuthService authService) {
//        this.authService = authService;
//    }
//
//    @PostMapping
//    public ResponseEntity<?> signupUser(@RequestBody SignupRequest signupRequest) {
//        try {
//            User createdUser = authService.createUser(signupRequest);
//            return ResponseEntity.status(HttpStatus.CREATED).body(createdUser);
//        } catch (RuntimeException e) {
//            if (e.getMessage().equals("Email already exists")) {
//                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Email already exists");
//            } else {
//                return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("An error occurred");
//            }
//        }
//    }
//
//}
