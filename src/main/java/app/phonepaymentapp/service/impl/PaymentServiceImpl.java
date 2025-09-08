package app.phonepaymentapp.service.impl;

import app.phonepaymentapp.dto.PaymentRequestDto;
import app.phonepaymentapp.exception.InsufficientFundsException;
import app.phonepaymentapp.models.Payment;
import app.phonepaymentapp.models.User;
import app.phonepaymentapp.repository.PaymentRepository;
import app.phonepaymentapp.repository.UserRepository;
import app.phonepaymentapp.service.PaymentService;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.awt.print.Pageable;
import java.time.LocalDateTime;

@Service
public class PaymentServiceImpl implements PaymentService {
    private final UserRepository userRepository;
    private final PaymentRepository paymentRepository;

    public PaymentServiceImpl(UserRepository userRepository, PaymentRepository paymentRepository) {
        this.userRepository = userRepository;
        this.paymentRepository = paymentRepository;
    }

    @Override
    @Transactional
    public Payment makePayment(String userLogin, PaymentRequestDto paymentRequest) {
        User userAccount = userRepository.findByLogin(userLogin);

        if (userAccount.getBalance().compareTo(paymentRequest.getAmount()) < 0) {
            throw new InsufficientFundsException("Insufficient funds");
        }

        userAccount.setBalance(userAccount.getBalance().subtract(paymentRequest.getAmount()));

        Payment response = Payment.builder()
                .targetPhoneNumber(paymentRequest.getTargetPhoneNumber())
                .amount(paymentRequest.getAmount())
                .paymentDate(LocalDateTime.now())
                .user(userAccount)
                .build();

        userAccount.getPayments().add(response);
        return paymentRepository.save(response);
    }

    @Override
    public Page<Payment> getPaymentHistory(Long userId, Pageable pageable) {
        return null;
    }
}
