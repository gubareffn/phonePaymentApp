package app.phonepaymentapp.service.impl;

import app.phonepaymentapp.dto.UserBalanceDto;
import app.phonepaymentapp.dto.UserRegistrationDto;
import app.phonepaymentapp.models.User;
import app.phonepaymentapp.repository.UserRepository;
import app.phonepaymentapp.service.UserService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import java.math.BigDecimal;

@Service
public class UserServiceImpl implements UserService {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    public UserServiceImpl(UserRepository userRepository, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public ResponseEntity<?> createUser(UserRegistrationDto user) {
        if (userRepository.existsByLogin(user.getLogin()))
            return ResponseEntity.badRequest().body("User with that phone number already exists");

        User createdUser = User.builder()
                .login(user.getLogin())
                .password(passwordEncoder.encode(user.getPassword()))
                .firstName(user.getFirstName())
                .middleName(user.getMiddleName())
                .lastName(user.getLastName())
                .dateOfBirth(user.getDateOfBirth())
                .gender(user.getGender())
                .balance(BigDecimal.valueOf(1000.00))
                .build();
        userRepository.save(createdUser);
        return new ResponseEntity<>(HttpStatus.CREATED);
    }

    @Override
    public UserBalanceDto getUserBalance(String userLogin) {
        User userAccount = userRepository.findByLogin(userLogin);

        return UserBalanceDto.builder()
                .login(userAccount.getLogin())
                .balance(userAccount.getBalance())
                .build();
    }

}
