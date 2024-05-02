package club.esprit.backend.services;

import club.esprit.backend.dto.SignupRequest;
import club.esprit.backend.entities.Role;
import club.esprit.backend.entities.User;
import club.esprit.backend.repository.UserRepository;
import club.esprit.backend.utils.OtpUtil;
import jakarta.mail.MessagingException;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.Duration;
import java.time.LocalDateTime;

@Service
public class IAuthImpl implements IAuth {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private OtpUtil otpUtil;
    private EmailService emailUtil;

    @Autowired
    public IAuthImpl(UserRepository userRepository, PasswordEncoder passwordEncoder,
                     OtpUtil otpUtil, EmailService emailUtil) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
        this.otpUtil = otpUtil;
        this.emailUtil = emailUtil;

    }


    @Override
    public User createUser(SignupRequest signupRequest) {
        // Check if user already exists
        if (userRepository.existsByEmail(signupRequest.getEmail())) {
            throw new RuntimeException("Email already exists");
        }

        User user = new User();
        BeanUtils.copyProperties(signupRequest, user);

        user.setRole(Role.MEMBRE);
        user.setEtat("normal");

        // Generate and set OTP for the user
        String otp = otpUtil.generateOtp();  // Implement this method to generate an OTP
        user.setOtp(otp);

        user.setOtpGeneratedTime(LocalDateTime.now());

        // Hash the password before saving
        String hashPassword = passwordEncoder.encode(signupRequest.getPassword());
        user.setPassword(hashPassword);

        // Save the user
        User createdUser = userRepository.save(user);

        // Send verification email
        try {
            emailUtil.sendOtpEmail(signupRequest.getEmail(), user.getOtp());
        } catch (MessagingException e) {
            throw new RuntimeException("Failed to send verification email. Please try again.");
        }

        return createdUser;
    }



    @Override
    public String verifyAccount(String email, String otp) {
        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("User not found with this email: " + email));
        if (user.getOtp().equals(otp) && Duration.between(user.getOtpGeneratedTime(), LocalDateTime.now()).getSeconds() < (1 * 60)) {
            // OTP is valid and not expired
            user.setActive(true);
            userRepository.save(user);
            return "OTP verified you can login";
        }

        return "Please regenerate otp and try again";
    }

    @Override
    public String regenerateOtp(String email) {
        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("User not found with this email: " + email));
        String otp = otpUtil.generateOtp();
        try {
            emailUtil.sendOtpEmail(email, otp);
        } catch (MessagingException e) {
            throw new RuntimeException("Unable to send otp please try again");
        }
        user.setOtp(otp);
        user.setOtpGeneratedTime(LocalDateTime.now());
        userRepository.save(user);
        return "Email sent... please verify account within 1 minute";
    }

    @Override
    public String forgotPassword(String email) {
        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("User not found with this email: " + email));
        try {
            emailUtil.sendSetPasswordEmail(email);
        } catch (MessagingException e) {
            throw new RuntimeException("Unable to send set password email please try again");
        }
        return "Email sent... please check your email to set new password";

    }

    @Override
    public String setPassword(String email, String newPassword) {
        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("User not found with this email: " + email));
        String hashPassword = passwordEncoder.encode(newPassword);
        user.setPassword(hashPassword);
        userRepository.save(user);
        return "Password set successfully";
    }
}
