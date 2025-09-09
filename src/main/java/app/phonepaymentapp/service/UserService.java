package app.phonepaymentapp.service;

import app.phonepaymentapp.dto.UserBalanceDto;
import app.phonepaymentapp.dto.UserProfileDto;
import app.phonepaymentapp.dto.UserRegistrationDto;
import app.phonepaymentapp.models.User;


public interface UserService {
    /**
     * Регистрация пользователя
     */
    void createUser(UserRegistrationDto user);

    /**
     * Получение баланса пользователя
     */
    UserBalanceDto getUserBalance(String userLogin);

    /**
     * Изменение данных профиля пользователя
     */
    User editProfileData(String userLogin, UserProfileDto user);
}
