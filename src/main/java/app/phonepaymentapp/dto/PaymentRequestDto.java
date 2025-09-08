package app.phonepaymentapp.dto;

import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class PaymentRequestDto {
    @NotBlank
    @Pattern(regexp = "^\\+7\\d{10}$")
    private String targetPhoneNumber;

    @NotNull
    @DecimalMin(value = "0.01")
    private BigDecimal amount;
}
