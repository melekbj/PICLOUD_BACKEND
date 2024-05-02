package club.esprit.backend.services;


import club.esprit.backend.entities.Role;
import club.esprit.backend.entities.User;

import java.util.List;

public interface IUser {

    void setNewRole(User user, Role newRole);

    List<User> getAllUsers();

    List<User> getUsersByRole(String role);

    void setEtatToPending(Long id);
    void setEtatToAccepted(Long id);
    void setEtatToRejected(Long id);
}
