package recipes.controller;

import jakarta.validation.ConstraintViolationException;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.*;
import recipes.dto.AuthUserDTO;
import recipes.service.UserService;

import java.util.Map;

@RestController
@RequestMapping("/api")
public class AuthUserRestController {

    private final UserService userService;

    public AuthUserRestController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping("/register")
    public ResponseEntity<Void> registerUser(@Valid @RequestBody AuthUserDTO user) {
        if (userService.userExistsByEmail(user.getEmail())) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }

        userService.registerUser(user);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @ExceptionHandler({MethodArgumentNotValidException.class,
                       ConstraintViolationException.class})
    public ResponseEntity<Map<String, String>> handleValidationExceptions() {
        return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
    }

}
