package club.esprit.backend.services;

import club.esprit.backend.dto.SignupRequest;
import club.esprit.backend.entities.User;


public interface AuthService {
    User createUser(SignupRequest signupRequest);
}
