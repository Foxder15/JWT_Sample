package foxder.app.demo.service;

import foxder.app.demo.dto.RequestUser;
import foxder.app.demo.dto.ResponseUser;
import foxder.app.demo.exception.DuplicatedResource;
import foxder.app.demo.exception.UserNotFound;
import foxder.app.demo.model.User;
import foxder.app.demo.repository.UserRepository;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@FieldDefaults(makeFinal = true, level = AccessLevel.PRIVATE)
@RequiredArgsConstructor
public class UserService {
    UserRepository userRepository;

    @Transactional
    public ResponseUser postUser(RequestUser requestUser) {
        if (this.userRepository.findByEmail(requestUser.getEmail()).isPresent()) {
            throw new DuplicatedResource("User's already existed.");
        }

        User dbUser = new User();
        dbUser.setEmail(requestUser.getEmail());
        dbUser.setPassword(requestUser.getPassword());
        dbUser.setRole(requestUser.getRole());
        dbUser = this.userRepository.save(dbUser);

        return new ResponseUser(dbUser.getId(), dbUser.getEmail(), dbUser.getRole());
    }

    @Transactional(readOnly = true)
    public List<ResponseUser> getAllUsersFromDB() {
        List<User> dbUsers = this.userRepository.findAll();

        return dbUsers.stream().map(user -> {
            return new ResponseUser(user.getId(), user.getEmail(), user.getRole());
        }).toList();
    }

    @Transactional(readOnly = true)
    public ResponseUser getUserByUserId(String id) {
        User dbUser = this.userRepository.findById(id).orElseThrow(() -> new UserNotFound("User not found"));

        return new ResponseUser(dbUser.getId(), dbUser.getEmail(), dbUser.getRole());
    }

}
