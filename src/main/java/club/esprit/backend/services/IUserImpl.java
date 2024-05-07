package club.esprit.backend.services;


import club.esprit.backend.entities.Role;
import club.esprit.backend.entities.User;
import club.esprit.backend.repository.UserRepository;
import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
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
    public List<User> getUsersByRoleAndEtat(String role, String etat) {
        Role roleEnum = Role.valueOf(role.toUpperCase());
        return userRepository.findAll().stream()
                .filter(user -> user.getRole().equals(roleEnum) && user.getEtat().equalsIgnoreCase(etat))
                .collect(Collectors.toList());
    }


    @Override
    public Optional<User> findUserByEmail(String email){
        return userRepository.findByEmail(email);
    }

    @Override
    @Transactional
    public void updateUserProfile(Long userId, User updatedUser) {
        userRepository.findById(userId).ifPresent(user -> {
            user.setName(updatedUser.getName());
//            user.setEmail(updatedUser.getEmail());
            user.setPassword(updatedUser.getPassword());
            user.setProfileImage(updatedUser.getProfileImage());
            userRepository.save(user);
        });
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
    public boolean setEtatToAccepted(Long id) {
        User user = userRepository.findById(id).orElse(null);
        if (user != null) {
            user.setEtat("accepted");
            user.setRole(Role.RESPONSABLE);
            userRepository.save(user);
            return true;
        }else {
            return false;
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
