package app.phonepaymentapp.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;
import lombok.Data;

import java.math.BigDecimal;

@Data
@Builder
@Schema(description = "Получение баланса пользователя")
public class UserBalanceDto {

    @Schema(description = "Логин пользователя(номер телефона)", example = "+73151392683")
    private String login;

    @Schema(description = "Баланс пользователя", example = "200.0")
    private BigDecimal balance;
}
