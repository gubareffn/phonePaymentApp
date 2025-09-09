package app.phonepaymentapp.service.impl;

import app.phonepaymentapp.dto.UserBalanceDto;
import app.phonepaymentapp.dto.UserProfileDto;
import app.phonepaymentapp.dto.UserRegistrationDto;
import app.phonepaymentapp.exception.UserAlreadyExistsException;
import app.phonepaymentapp.exception.UserNotFoundException;
import app.phonepaymentapp.models.User;
import app.phonepaymentapp.repository.UserRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class UserServiceTest {
    @Mock
    private UserRepository userRepository;

    @InjectMocks
    private UserServiceImpl userServiceImpl;

    @Test
    void getUserBalance_ShouldReturnBalance_WhenUserExists() {
        String userLogin = "+79123456789";
        User user = User.builder()
                .login(userLogin)
                .balance(BigDecimal.valueOf(1000.00))
                .build();

        when(userRepository.findByLogin(userLogin)).thenReturn(Optional.of(user));

        UserBalanceDto result = userServiceImpl.getUserBalance(userLogin);

        assertNotNull(result);
        assertEquals(userLogin, result.getLogin());
    }

    @Test
    void getUserBalance_ShouldThrowException_WhenUserNotFound() {
        String userLogin = "+79123456789";
        when(userRepository.findByLogin(userLogin)).thenReturn(Optional.empty());

        assertThrows(UserNotFoundException.class, () -> userServiceImpl.getUserBalance(userLogin));
        verify(userRepository).findByLogin(userLogin);
    }

    @Test
    void editProfileData_ShouldUpdateField_WhenValidData() {
        String userLogin = "+79123456789";
        User existingUser = User.builder()
                .login(userLogin)
                .firstName("OldName")
                .build();

        UserProfileDto newData = UserProfileDto.builder()
                .firstName("NewName")
                .build();

        when(userRepository.findByLogin(userLogin)).thenReturn(Optional.of(existingUser));
        when(userRepository.save(any(User.class))).thenAnswer(invocation -> invocation.getArgument(0));

        User result = userServiceImpl.editProfileData(userLogin, newData);

        assertNotNull(result);
        assertEquals("NewName", result.getFirstName());
    }

    @Test
    void editProfileData_ShouldNotUpdateTheField_WhenTheFieldIsNotMentioned() {
        String userLogin = "+79123456789";
        User existingUser = User.builder()
                .login(userLogin)
                .firstName("OldName")
                .build();

        UserProfileDto newData = UserProfileDto.builder()
                .lastName("NewLastName")
                .build();

        when(userRepository.findByLogin(userLogin)).thenReturn(Optional.of(existingUser));
        when(userRepository.save(any(User.class))).thenAnswer(invocation -> invocation.getArgument(0));

        User result = userServiceImpl.editProfileData(userLogin, newData);

        assertNotNull(result);
        assertEquals("OldName", result.getFirstName());
    }

    @Test
    void createUser_ShouldCallRepository() {
        final UserRegistrationDto dto = mock(UserRegistrationDto.class);

        userServiceImpl.createUser(dto);

        verify(userRepository).save(any(User.class));
    }

    @Test
    void createUser_ShouldThrowExceptionWhenUserExists() {
        UserRegistrationDto dto = UserRegistrationDto.builder()
                .login("+79123456789")
                .password("password123")
                .build();

        when(userRepository.existsByLogin("+79123456789")).thenReturn(true);

        assertThrows(UserAlreadyExistsException.class, () -> userServiceImpl.createUser(dto));
        verify(userRepository, never()).save(any(User.class));
    }

}