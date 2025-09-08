package app.phonepaymentapp.controllers;

import app.phonepaymentapp.dto.PaymentRequestDto;
import app.phonepaymentapp.service.PaymentService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Validated
@RestController
@RequestMapping("/api/payment")
public class PaymentController {
    private final PaymentService paymentService;

    public PaymentController(PaymentService paymentService) {
        this.paymentService = paymentService;
    }

    @PostMapping("/create")
    public ResponseEntity<?> makePayment(Authentication authentication, @Valid @RequestBody PaymentRequestDto payment) {
        paymentService.makePayment(authentication.getName(), payment);
        return new ResponseEntity<>("Payment was successfully made", HttpStatus.OK);
    }
}
