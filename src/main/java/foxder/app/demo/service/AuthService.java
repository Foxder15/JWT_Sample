package foxder.app.demo.service;

import foxder.app.demo.dto.UserLogin;
import foxder.app.demo.exception.UserNotFound;
import foxder.app.demo.model.User;
import foxder.app.demo.model.UserPrincipal;
import foxder.app.demo.repository.UserRepository;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
@FieldDefaults(makeFinal = true, level = AccessLevel.PRIVATE)
@RequiredArgsConstructor
public class AuthService {
    AuthenticationManager authenticationManager;
    UserRepository userRepository;
    JwtService jwtService;

    public String login(UserLogin userLogin) {
        Authentication authentication = authenticationManager
                .authenticate(new UsernamePasswordAuthenticationToken(userLogin.getEmail(), userLogin.getPassword()));

        if (authentication.isAuthenticated()) {
            UserPrincipal userPrincipal = (UserPrincipal) authentication.getPrincipal();
            return jwtService.generateToken(userPrincipal);
        }

        return "";
    }
}
