package foxder.app.demo.controller;

import foxder.app.demo.dto.UserLogin;
import foxder.app.demo.service.AuthService;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/auth")
@FieldDefaults(makeFinal = true, level = AccessLevel.PRIVATE)
@RequiredArgsConstructor
public class AuthController {
    AuthService authService;

    @PostMapping("/login")
    public ResponseEntity<String> login(@RequestBody UserLogin userLogin) {
        return ResponseEntity.ok(this.authService.login(userLogin));
    }
}
