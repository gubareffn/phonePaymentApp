package app.phonepaymentapp.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import lombok.Builder;
import lombok.Data;

import java.math.BigDecimal;

@Data
@Builder
@Schema(description = "Запрос на выполнение платежа")
public class PaymentRequestDto {

    @Schema(description = "Номер телефона получателя", example = "+79123456789")
    @NotBlank
    @Pattern(regexp = "^\\+7\\d{10}$")
    private String targetPhoneNumber;

    @Schema(description = "Сумма платежа в рублях", example = "150.50", minimum = "0.01")
    @NotNull
    @DecimalMin(value = "0.01")
    private BigDecimal amount;
}
