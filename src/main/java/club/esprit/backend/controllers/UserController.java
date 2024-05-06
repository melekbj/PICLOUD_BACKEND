package club.esprit.backend.controllers;

import club.esprit.backend.entities.Role;
import club.esprit.backend.entities.User;
import club.esprit.backend.repository.UserRepository;
import club.esprit.backend.services.IUser;
import club.esprit.backend.utils.UserUtil;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
    @RequestMapping("/users")
@AllArgsConstructor
public class UserController {

    private final IUser iUser;
    private final UserRepository userRepository;
    private final UserUtil userUtil;

    @PutMapping("/{id}/role")
    public ResponseEntity<?> setNewRole(@PathVariable Long id, @RequestBody Role newRole) {
        User user = userRepository.findById(id).orElse(null);
        if (user == null) {
            return ResponseEntity.notFound().build();
        }
        iUser.setNewRole(user, newRole);
        return ResponseEntity.ok().build();
    }

    @GetMapping("/allUsers")
    public ResponseEntity<?> getAllUsers() {
        return ResponseEntity.ok(iUser.getAllUsers());
    }

    @GetMapping("/usersByRole/{role}")
    public ResponseEntity<?> getUsersByRole(@PathVariable String role) {
        return ResponseEntity.ok(iUser.getUsersByRole(role));
    }

    @GetMapping("/api/user")
    public ResponseEntity<?> getUser() {
        UserDetails userDetails = userUtil.getAuthenticatedUser();
        if (userDetails != null) {
            return ResponseEntity.ok(userDetails);
        } else {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("User not authenticated");
        }
    }


    @PutMapping("/{id}/pending")
    public void setPending(@PathVariable Long id) {
        iUser.setEtatToPending(id);
    }

    @PutMapping("/{id}/accepted")
    public void setAccepted(@PathVariable Long id) {
        iUser.setEtatToAccepted(id);
    }

    @PutMapping("/{id}/rejected")
    public void setRejected(@PathVariable Long id) {
        iUser.setEtatToRejected(id);
    }


    @GetMapping("/findByEmail/{email}")
    public Optional<User> getUserByEmail(@PathVariable String email){
        return iUser.findUserByEmail(email) ;
    }

}
