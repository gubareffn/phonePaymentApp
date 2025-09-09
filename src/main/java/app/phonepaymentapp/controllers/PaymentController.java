package app.phonepaymentapp.controllers;

import app.phonepaymentapp.dto.PaymentRequestDto;
import app.phonepaymentapp.models.Payment;
import app.phonepaymentapp.service.PaymentService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import jakarta.validation.Valid;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@Validated
@RestController
@RequestMapping("/api/payment")
public class PaymentController {
    private final PaymentService paymentService;

    public PaymentController(PaymentService paymentService) {
        this.paymentService = paymentService;
    }

    @Operation(summary = "Выполнение оплаты по заданному номеру и сумме платежа")
    @PostMapping("/create")
    public ResponseEntity<?> makePayment(Authentication authentication, @Valid @RequestBody PaymentRequestDto payment) {
        paymentService.makePayment(authentication.getName(), payment);
        return new ResponseEntity<>("Payment was successfully made", HttpStatus.OK);
    }

    @Operation(summary = "Получение списка платежей авторизованного пользователя")
    @GetMapping("/payment-history")
    public ResponseEntity<Page<Payment>> paymentHistory(Authentication authentication,
                                                        @RequestParam(defaultValue = "0") @Parameter(description = "Номер страницы") int page,
                                                        @RequestParam(defaultValue = "10") @Parameter(description = "Число элементов")int size) {
        return new ResponseEntity<>(paymentService.getPaymentHistory(authentication.getName(), PageRequest.of(page, size)), HttpStatus.OK);
    }
}
