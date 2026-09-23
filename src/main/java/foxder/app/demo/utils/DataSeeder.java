package foxder.app.demo.utils;

import foxder.app.demo.model.User;
import foxder.app.demo.repository.UserRepository;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Component;

import java.security.SecureRandom;

//@Component
@FieldDefaults(makeFinal = true, level = AccessLevel.PRIVATE)
@RequiredArgsConstructor
public class DataSeeder implements CommandLineRunner {
    UserRepository userRepository;
    BCryptPasswordEncoder bCryptPasswordEncoder;

    @Override
    public void run(String... args) throws Exception {
        String[] roles = {"admin", "user", "ban_user"};
        String[] mailList = {"abc", "bdc", "dca", "aka", "kol"};
        String[] passwords = {"Abc@123456", "Ksm@9118876", "Daa@2345679", "Lcw@321456"};
        SecureRandom random = new SecureRandom();

        for (int i = 1; i <= 1000; i++) {
            int roleIndex = random.nextInt(3);
            int mailIndex = random.nextInt(mailList.length);
            int passwordIndex = random.nextInt(passwords.length);

            User user = new User();
            user.setEmail(mailList[mailIndex] + i + "@gmail.com");
            user.setPassword(this.bCryptPasswordEncoder.encode(passwords[passwordIndex]));
            user.setRole(roles[roleIndex]);
            this.userRepository.save(user);
        }
    }
}
