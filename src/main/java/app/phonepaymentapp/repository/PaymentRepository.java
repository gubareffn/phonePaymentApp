package app.phonepaymentapp.repository;

import app.phonepaymentapp.models.Payment;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PaymentRepository  extends JpaRepository<Payment, Integer> {
    Page<Payment> findByUserId(Long userId, Pageable pageable);
}
