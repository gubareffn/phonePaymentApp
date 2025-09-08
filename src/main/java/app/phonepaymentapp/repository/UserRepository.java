package app.phonepaymentapp.repository;

import app.phonepaymentapp.models.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Integer> {
    boolean existsByLogin(String login);

    User findByLogin(String username);
}
