package io.github.joherrer.cashcards.controller;

import io.github.joherrer.cashcards.dto.User;
import io.github.joherrer.cashcards.model.UserEntity;
import io.github.joherrer.cashcards.service.CashCardUserDetailsService;
import jakarta.validation.Valid;
import org.springframework.context.annotation.Profile;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@Profile("!test")
@RestController
@RequestMapping("/users")
public class UserController {
    private final CashCardUserDetailsService userService;

    public UserController(CashCardUserDetailsService userService) {
        this.userService = userService;
    }

    @PostMapping("/register")
    public ResponseEntity<String> registerUser(@Valid @RequestBody User user) {
        if (userService.findByUsername(user.username()).isPresent()) {
            return ResponseEntity.badRequest().body("Username already exists.");
        }
        UserEntity newUser = userService.createUser(user.username(), user.password());
        return ResponseEntity.ok("User registered: " + newUser.getUsername());
    }
}
