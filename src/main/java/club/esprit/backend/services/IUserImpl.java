package club.esprit.backend.services;


import club.esprit.backend.entities.Role;
import club.esprit.backend.entities.User;
import club.esprit.backend.repository.UserRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;


@Service
@AllArgsConstructor
public class IUserImpl implements IUser {


    private UserRepository userRepository;

    @Override
    public void setNewRole(User user, Role newRole) {
        user.setRole(newRole);
        userRepository.save(user);
    }

    @Override
    public List<User> getAllUsers() {
        return userRepository.findAll();
    }

    @Override
    public List<User> getUsersByRole(String role) {
        Role roleEnum = Role.valueOf(role.toUpperCase());
        return userRepository.findAll().stream()
                .filter(user -> user.getRole().equals(roleEnum))
                .collect(Collectors.toList());
    }

    @Override
    public void setEtatToPending(Long id) {
        User user = userRepository.findById(id).orElse(null);
        if (user != null) {
            user.setEtat("pending");
            userRepository.save(user);
        }
    }
    @Override
    public void setEtatToAccepted(Long id) {
        User user = userRepository.findById(id).orElse(null);
        if (user != null) {
            user.setEtat("accepted");
            userRepository.save(user);
        }
    }
    @Override
    public void setEtatToRejected(Long id) {
        User user = userRepository.findById(id).orElse(null);
        if (user != null) {
            user.setEtat("rejected");
            userRepository.save(user);
        }
    }
}
