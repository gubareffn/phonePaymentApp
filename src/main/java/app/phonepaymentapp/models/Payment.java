package app.phonepaymentapp.models;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
@Entity
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "payment")
public class Payment {
    /**
     * Идентификатор платежа
     */
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    @Column(name = "payment_id", nullable = false)
    private Long id;

    /**
     * Дата и время выполнения платежа
     */
    @Column(name = "date", nullable = false)
    private LocalDateTime paymentDate;

    /**
     * Номер телефона, на который должна быть произведена оплата
     */
    @Column(name = "target_phone_number", nullable = false)
    private String targetPhoneNumber;

    /**
     * Сумма платежа
     */
    @Column(name = "amount", precision = 10, scale = 2)
    private BigDecimal amount;

    @JsonIgnore
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User user;
}
