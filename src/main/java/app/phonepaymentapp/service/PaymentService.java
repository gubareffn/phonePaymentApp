package app.phonepaymentapp.service;

import app.phonepaymentapp.dto.PaymentRequestDto;
import app.phonepaymentapp.models.Payment;
import org.springframework.data.domain.Page;

import java.awt.print.Pageable;

public interface PaymentService {
    Payment makePayment(String userLogin, PaymentRequestDto paymentRequest);

    Page<Payment> getPaymentHistory(Long userId, Pageable pageable);
}
