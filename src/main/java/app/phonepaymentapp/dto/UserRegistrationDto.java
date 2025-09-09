package app.phonepaymentapp.dto;

import app.phonepaymentapp.models.Gender;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.*;
import lombok.Builder;
import lombok.Data;

import java.time.LocalDate;

@Data
@Builder
@Schema(description = "Данные для регистрации нового пользователя")
public class UserRegistrationDto {

    @Schema( description = "Логин (номер телефона в формате +7XXXXXXXXXX)", example = "+79123456789", requiredMode = Schema.RequiredMode.REQUIRED)
    @NotBlank(message = "Login is required")
    @Pattern(regexp = "^\\+7\\d{10}$", message = "Phone number must be in format +7XXXXXXXXXX")
    private String login;

    @Schema(description = "Пароль (минимум 6 символов)", example = "password123", minLength = 6, requiredMode = Schema.RequiredMode.REQUIRED)
    @NotBlank(message = "Password is required")
    @Size(min = 6, message = "Password must be at least 6 characters")
    private String password;

    @Schema(description = "Имя пользователя", example = "Иван")
    private String firstName;

    @Schema(description = "Отчество пользователя", example = "Иванович")
    private String middleName;

    @Schema(description = "Фамилия пользователя", example = "Иванов")
    private String lastName;

    @Schema(description = "Пол пользователя: MALE - мужской, FEMALE - женский", example = "MALE")
    private Gender gender;

    @Schema(description = "Email пользователя", example = "ivan.ivanov@example.com", format = "email")
    @Email(message = "Email should be valid")
    private String email;

    @Schema(description = "Дата рождения пользователя", example = "1990-01-15", requiredMode = Schema.RequiredMode.REQUIRED, format = "date")
    @Past(message = "Date of birth must be in the past")
    private LocalDate dateOfBirth;
}
