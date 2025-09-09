package app.phonepaymentapp.controllers;

import app.phonepaymentapp.dto.UserProfileDto;
import app.phonepaymentapp.service.UserService;
import io.swagger.v3.oas.annotations.Operation;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/users")
public class UserController {
    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @Operation(summary = "Получение баланса авторизованного пользователя")
    @GetMapping("/balance")
    public ResponseEntity<?> getUserBalance(Authentication authentication) {
        return ResponseEntity.ok(userService.getUserBalance(authentication.getName()));
    }

    @Operation(summary = "Изменение данных профиля авторизованного пользователя")
    @PutMapping("/edit-profile")
    public ResponseEntity<?> editProfileData(Authentication authentication,
                                             @Valid @RequestBody UserProfileDto profileUpdateRequest) {
        return ResponseEntity.ok(userService.editProfileData(authentication.getName(), profileUpdateRequest));
    }
}
