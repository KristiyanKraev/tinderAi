package projects.koko.tinder_backend.user;

import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/users")
public class UserController {
    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/me")
    public ResponseEntity<User> authenticatedUser(){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        User currUser = (User) authentication.getPrincipal();
        return ResponseEntity.ok(currUser);
    }

    @GetMapping("/")
    public ResponseEntity<List<User>> getAllUsers(){
       List<User> users = userService.getAllUsers();
       return ResponseEntity.ok(users);
    }
}
