package app.phonepaymentapp.dto;

import app.phonepaymentapp.models.Gender;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Past;
import lombok.Builder;
import lombok.Data;

import java.time.LocalDate;

@Data
@Builder
@Schema(description = "Профильные данные пользователя для редактирования")
public class UserProfileDto {

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

    @Schema(description = "Дата рождения пользователя", example = "1990-01-15", format = "date")
    @Past(message = "Date of birth must be in the past")
    private LocalDate dateOfBirth;
}
