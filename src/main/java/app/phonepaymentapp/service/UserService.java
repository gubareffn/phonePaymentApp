package app.phonepaymentapp.service;

import app.phonepaymentapp.dto.UserBalanceDto;
import app.phonepaymentapp.dto.UserRegistrationDto;
import org.springframework.http.ResponseEntity;

public interface UserService {

    ResponseEntity<?> createUser(UserRegistrationDto user);

    UserBalanceDto getUserBalance(String userLogin);

}
