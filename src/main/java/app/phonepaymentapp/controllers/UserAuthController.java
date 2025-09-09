package app.phonepaymentapp.controllers;

import app.phonepaymentapp.dto.UserRegistrationDto;
import app.phonepaymentapp.service.UserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
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

    @Operation(summary = "Регистрация пользователя", description = "Создает нового пользователя с начальным балансом 1000 рублей")
    @ApiResponse(responseCode = "201", description = "Пользователь успешно создан")
    @ApiResponse(responseCode = "400", description = "Неверные входные данные или пользователь уже существует")
    @PostMapping("/registration")
    public ResponseEntity<?> createUser(@Valid @RequestBody UserRegistrationDto user) {
        userService.createUser(user);
        return new ResponseEntity<>("User was created successfully", HttpStatus.OK);
    }
}
