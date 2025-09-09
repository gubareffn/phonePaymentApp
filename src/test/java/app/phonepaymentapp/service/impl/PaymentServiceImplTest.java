package app.phonepaymentapp.service.impl;

import app.phonepaymentapp.dto.PaymentRequestDto;
import app.phonepaymentapp.exception.InsufficientFundsException;
import app.phonepaymentapp.exception.UserNotFoundException;
import app.phonepaymentapp.models.Payment;
import app.phonepaymentapp.models.User;
import app.phonepaymentapp.repository.PaymentRepository;
import app.phonepaymentapp.repository.UserRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

import java.math.BigDecimal;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class PaymentServiceImplTest {
    @Mock
    private PaymentRepository paymentRepository;

    @Mock
    private UserRepository userRepository;

    @InjectMocks
    private PaymentServiceImpl paymentServiceImpl;

    @Test
    void makePayment_ShouldProcessPayment_WhenDataIsValid() {
        String userLogin = "+79123456789";
        User user = User.builder()
                .id(1L)
                .login(userLogin)
                .balance(BigDecimal.valueOf(1000.00))
                .build();

        PaymentRequestDto paymentRequest = PaymentRequestDto.builder()
                .targetPhoneNumber("+79127654321")
                .amount(BigDecimal.valueOf(500.00))
                .build();

        when(userRepository.findByLogin(userLogin)).thenReturn(Optional.of(user));
        when(paymentRepository.save(any(Payment.class))).thenAnswer(invocation -> invocation.getArgument(0));

        paymentServiceImpl.makePayment(userLogin, paymentRequest);

        assertEquals(BigDecimal.valueOf(500.00), user.getBalance()); // баланс уменьшился
        verify(paymentRepository).save(any(Payment.class));
        verify(userRepository).findByLogin(userLogin);
    }

    @Test
    void makePayment_ShouldThrowException_WhenInsufficientFunds() {
        String userLogin = "+79123456789";
        User user = User.builder()
                .login(userLogin)
                .balance(BigDecimal.valueOf(100.00))
                .build();

        PaymentRequestDto paymentRequest = PaymentRequestDto.builder()
                .targetPhoneNumber("+79127654321")
                .amount(BigDecimal.valueOf(500.00))
                .build();

        when(userRepository.findByLogin(userLogin)).thenReturn(Optional.of(user));

        assertThrows(InsufficientFundsException.class,
                () -> paymentServiceImpl.makePayment(userLogin, paymentRequest));

        verify(paymentRepository, never()).save(any(Payment.class));
    }

    @Test
    void makePayment_ShouldThrowException_WhenUserNotFound() {
        String userLogin = "+79123456789";
        PaymentRequestDto paymentRequest = PaymentRequestDto.builder()
                .targetPhoneNumber("+79127654321")
                .amount(BigDecimal.valueOf(100.00))
                .build();

        when(userRepository.findByLogin(userLogin)).thenReturn(Optional.empty());

        assertThrows(UserNotFoundException.class,
                () -> paymentServiceImpl.makePayment(userLogin, paymentRequest));

        verify(paymentRepository, never()).save(any(Payment.class));
    }

    @Test
    void getPaymentHistory_ShouldReturnPayments_WhenUserExists() {
        String userLogin = "+79123456789";
        User user = User.builder()
                .id(1L)
                .login(userLogin)
                .build();

        Page<Payment> expectedPage = Page.empty();
        Pageable pageable = PageRequest.of(0, 10);

        when(userRepository.findByLogin(userLogin)).thenReturn(Optional.of(user));
        when(paymentRepository.findByUserId(1L, pageable)).thenReturn(expectedPage);

        Page<Payment> result = paymentServiceImpl.getPaymentHistory(userLogin, pageable);

        assertEquals(expectedPage, result);
    }
}