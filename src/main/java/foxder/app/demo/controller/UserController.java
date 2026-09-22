package foxder.app.demo.controller;

import foxder.app.demo.dto.RequestUser;
import foxder.app.demo.dto.ResponseUser;
import foxder.app.demo.model.User;
import foxder.app.demo.service.UserService;
import jakarta.validation.Valid;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/users")
@FieldDefaults(makeFinal = true, level = AccessLevel.PRIVATE)
@RequiredArgsConstructor
public class UserController {

    UserService userService;

    @GetMapping
    public ResponseEntity<List<ResponseUser>> fetchAllUsers() {
        return ResponseEntity.ok(this.userService.getAllUsersFromDB());
    }

    @PostMapping
    public ResponseEntity<ResponseUser> addUser(@RequestBody @Valid RequestUser requestUser ) {
        ResponseUser responseUser = this.userService.postUser(requestUser);

        return ResponseEntity.status(201).body(responseUser);
    }
}
