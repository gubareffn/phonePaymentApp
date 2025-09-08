package app.phonepaymentapp.repository;

import app.phonepaymentapp.models.Payment;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PaymentRepository  extends JpaRepository<Payment, Integer> {

}
