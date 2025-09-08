package app.phonepaymentapp.controllers;

import app.phonepaymentapp.dto.UserRegistrationDto;
import app.phonepaymentapp.service.UserService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/auth")
@Validated
public class UserAuthController {
    private final UserService userService;

    public UserAuthController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping("/registration")
    public ResponseEntity<?> createUser(@Valid @RequestBody UserRegistrationDto user) {
        return userService.createUser(user);
    }
}
