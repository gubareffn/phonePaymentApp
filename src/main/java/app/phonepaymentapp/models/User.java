package app.phonepaymentapp.models;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Data
@Entity
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "users")

public class User {
    /**
     * Идентификатор пользователя
     */
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    @Column(name = "user_id", nullable = false)
    private Long id;

    /**
     * Логин пользователя(номер телефона в формате +7XXXXXXXXXX)
     */
    @Column(name = "login", nullable = false, length = 12)
    private String login;

    /**
     * Хэш пароля пользователя
     */
    @Column(name = "password", nullable = false)
    private String password;

    /**
     * Баланс пользователя
     */
    @Column(name = "balance", precision = 10, scale = 2)
    private BigDecimal balance;

    /**
     * Имя пользователя
     */
    @Column(name = "first_name")
    private String firstName;

    /**
     * Отчество
     */
    @Column(name = "middle_name")
    private String middleName;

    /**
     * Фамилия
     */
    @Column(name = "last_name")
    private String lastName;

    /**
     * Пол(MALE - мужской, FEMALE - женский)
     */
    @Enumerated(EnumType.STRING)
    @Column(name = "gender", length = 6)
    private Gender gender;

    /**
     * Дата рождения
     */
    @Column(name = "date_of_birth")
    private LocalDate dateOfBirth;

    /**
     * Электронный адрес
     */
    @Column(name = "email", unique = true, length = 100)
    private String email;

    @Builder.Default
    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL)
    private List<Payment> payments = new ArrayList<>();
}
