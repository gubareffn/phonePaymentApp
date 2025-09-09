package app.phonepaymentapp.service.impl;

import app.phonepaymentapp.dto.UserBalanceDto;
import app.phonepaymentapp.dto.UserProfileDto;
import app.phonepaymentapp.dto.UserRegistrationDto;
import app.phonepaymentapp.exception.UserAlreadyExistsException;
import app.phonepaymentapp.exception.UserNotFoundException;
import app.phonepaymentapp.models.User;
import app.phonepaymentapp.repository.UserRepository;
import app.phonepaymentapp.service.UserService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;

@Service
public class UserServiceImpl implements UserService {
    public static final BigDecimal INITIAL_BALANCE = BigDecimal.valueOf(1000.00);
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    public UserServiceImpl(UserRepository userRepository, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    /**
     * Получение баланса авторизованного пользователя
     *
     * @param userLogin логин авторизованного пользователя
     * @throws UserNotFoundException, если авторизованный пользователь не найден
     */
    @Override
    public UserBalanceDto getUserBalance(String userLogin) {
        User userAccount = userRepository.findByLogin(userLogin)
                .orElseThrow(() -> new UserNotFoundException("User not found: " + userLogin));

        return UserBalanceDto.builder()
                .login(userAccount.getLogin())
                .balance(userAccount.getBalance())
                .build();
    }

    /**
     * Изменение данных профиля пользователя
     *
     * @param userLogin   логин авторизованного пользователя
     * @param newUserData новые данные пользователя
     * @throws UserAlreadyExistsException, если пользователь с таким электронным адресом уже существует
     * @throws UserNotFoundException, если авторизованный пользователь не найден
     */
    @Override
    @Transactional
    public User editProfileData(String userLogin, UserProfileDto newUserData) {
        User userAccount = userRepository.findByLogin(userLogin)
                .orElseThrow(() -> new UserNotFoundException("User not found: " + userLogin));

        if (newUserData.getFirstName() != null) {
            userAccount.setFirstName(newUserData.getFirstName());
        }
        if (newUserData.getMiddleName() != null) {
            userAccount.setMiddleName(newUserData.getMiddleName());
        }
        if (newUserData.getLastName() != null) {
            userAccount.setLastName(newUserData.getLastName());
        }
        if (newUserData.getDateOfBirth() != null) {
            userAccount.setDateOfBirth(newUserData.getDateOfBirth());
        }
        if (newUserData.getGender() != null) {
            userAccount.setGender(newUserData.getGender());
        }
        if (newUserData.getEmail() != null) {
            if (!userRepository.existsByEmail(newUserData.getEmail()))
                userAccount.setEmail(newUserData.getEmail());
            else throw new UserAlreadyExistsException("User with that email already exists");
        }
        return userRepository.save(userAccount);
    }

    /**
     * Регистрация новго пользователя
     *
     * @param userData данные пользователя
     * @throws UserAlreadyExistsException, если пользователь с таким логином уже существует
     */
    @Override
    @Transactional
    public void createUser(UserRegistrationDto userData) {
        if (userRepository.existsByLogin(userData.getLogin()))
            throw new UserAlreadyExistsException("User with that phone number already exists");
        else userRepository.save(buildUser(userData));
    }

    /**
     * Сборка пользователя
     *
     * @param userData данные пользователя
     */
    private User buildUser(UserRegistrationDto userData) {
        return User.builder()
                .login(userData.getLogin())
                .password(passwordEncoder.encode(userData.getPassword()))
                .firstName(userData.getFirstName())
                .middleName(userData.getMiddleName())
                .lastName(userData.getLastName())
                .dateOfBirth(userData.getDateOfBirth())
                .gender(userData.getGender())
                .email(userData.getEmail())
                .balance(INITIAL_BALANCE)
                .build();
    }
}
