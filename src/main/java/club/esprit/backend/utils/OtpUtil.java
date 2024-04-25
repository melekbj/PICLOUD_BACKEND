package club.esprit.backend.utils;

import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.Random;

@Component
public class OtpUtil {

    public String generateOtp() {
        Random random = new Random();
        int randomNumber = random.nextInt(999999);
        String output = Integer.toString(randomNumber);

        while (output.length() < 6) {
            output = "0" + output;
        }
        return output;
    }

    public LocalDateTime generateExpirationTime() {
        return LocalDateTime.now().plus(30, ChronoUnit.MINUTES);  // OTP valid for 30 minutes
    }
}
