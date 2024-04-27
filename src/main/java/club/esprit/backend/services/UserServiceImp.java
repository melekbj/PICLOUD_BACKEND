package club.esprit.backend.services;

import club.esprit.backend.entities.Membership;
import club.esprit.backend.entities.User;
import club.esprit.backend.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
@Service

public class UserServiceImp implements UserService {

    private  UserRepository userRepository;

    @Autowired
    public UserServiceImp(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public User addUser(User user) {
        return userRepository.save(user);
    }

    @Override
    public User updateUser(User user) {
        return userRepository.save(user);
    }

    @Override
    public void deleteUser(Long id) {
        userRepository.deleteById(id);
    }

    @Override
    public User getUser(Long id) {
        Optional<User> optionalUser = userRepository.findById(id);
        return optionalUser.orElse(null);
    }

    @Override
    public List<User> getAllUsers() {
        return userRepository.findAll();
    }

    @Override
    public User getById(Long id) {
        return userRepository.findById(id).orElse(null);
    }
    @Override
    public List<Membership> getMembersForUser(Long userId) {
        User user = getById(userId);
        System.out.println("user" + user);

        return user.getMemberships();

    }

    @Override
    public List<User> getUsersByClub(Long id) {
        return userRepository.findUsersByClubId(id);
    }

    @Override
    public List<User> findUsersNotClub(Long id) {
        return userRepository.findUsersNotInClub(id);
    }

    @Override
    public List<User> findUsersByDepartmentId(Long departmentId) {
        return  userRepository.findUsersByDepartmentId(departmentId);
    }

    @Override
    public List<User> findMembersWithoutDepartment(Long id) {
        return userRepository.findMembersWithoutDepartment(id);
    }
}
