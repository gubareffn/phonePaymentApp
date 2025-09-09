package app.phonepaymentapp.dto;

import app.phonepaymentapp.models.Gender;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Past;
import jakarta.validation.constraints.Size;
import lombok.Builder;
import lombok.Data;

import java.time.LocalDate;

@Data
@Builder
@Schema(description = "Профильные данные пользователя для редактирования")
public class UserProfileDto {

    @Schema(description = "Имя пользователя", example = "Иван")
    @Size(min = 2, max = 50, message = "First name must be between 2 and 50 characters")
    private String firstName;

    @Schema(description = "Отчество пользователя", example = "Иванович")
    @Size(max = 50, message = "Middle name cannot exceed 50 characters")
    private String middleName;

    @Schema(description = "Фамилия пользователя", example = "Иванов")
    @Size(min = 2, max = 50, message = "Last name must be between 2 and 50 characters")
    private String lastName;

    @Schema(description = "Пол пользователя: MALE - мужской, FEMALE - женский", example = "MALE")
    private Gender gender;

    @Schema(description = "Email пользователя", example = "ivan.ivanov@example.com", format = "email")
    @Size(max = 100, message = "Email cannot exceed 100 characters")
    @Email(message = "Email should be valid")
    private String email;

    @Schema(description = "Дата рождения пользователя", example = "1990-01-15", format = "date")
    @Past(message = "Date of birth must be in the past")
    private LocalDate dateOfBirth;
}
