package app.phonepaymentapp.service;

import app.phonepaymentapp.dto.PaymentRequestDto;
import app.phonepaymentapp.models.Payment;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface PaymentService {
    /**
     * Выполнение оплаты
     */
    void makePayment(String userLogin, PaymentRequestDto paymentRequest);

    /**
     * Получение истории операций
     */
    Page<Payment> getPaymentHistory(String userId, Pageable pageable);
}
