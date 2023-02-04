package org.example.service;

import org.example.exception.UserNonUniqueException;
import org.example.model.User;
import org.example.repository.UserRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class UserServiceTest {
    @Mock
    private UserRepository userRepository;

    @InjectMocks
    private UserService userService;

    @Test
    public void GetAllLogin_WhenEmptyUserList_EmptyListReturned() {
        when(userRepository.getAllUsers()).thenReturn(Collections.emptyList());
        assertTrue(userService.getAllLogin().isEmpty());
    }

    @Test
    public void GetAllLogin_WithAnyUserList_CorrectLoginListReturned() {
        when(userRepository.getAllUsers()).thenReturn(List.of(
                new User("123", "123"),
                new User("test", "123"),
                new User("login", "password")
        ));
        assertTrue(userService.getAllLogin().equals(List.of("123","test","login")));
    }

    @Test
    public void AddNewUser_WithCorrectParametersAndWithIncorrectParameters_VoidReturnedAndExceptionThrows(){
        userService.addNewUser("login","password");
        verify(userRepository).findUserByLogin("login");
        verify(userRepository).addUser(any());

        assertThrows(IllegalArgumentException.class,()->userService.addNewUser("","password"));
        verify(userRepository).findUserByLogin(any());
        verify(userRepository).addUser(any());

        assertThrows(IllegalArgumentException.class,()->userService.addNewUser("",""));
        verify(userRepository).findUserByLogin(any());
        verify(userRepository).addUser(any());

        assertThrows(IllegalArgumentException.class,()->userService.addNewUser(null,null));
        verify(userRepository).findUserByLogin(any());
        verify(userRepository).addUser(any());

        assertThrows(IllegalArgumentException.class,()->userService.addNewUser("test",""));
        verify(userRepository).findUserByLogin(any());
        verify(userRepository).addUser(any());
    }

    @Test
    public void AddNewUser_WhenExistsUserWithCurrentLogin_ExceptionThrows(){
        when(userRepository.findUserByLogin(any())).thenReturn(
                Optional.of(new User("test","test")));

        assertThrows(UserNonUniqueException.class,
                ()->userService.addNewUser("test","test"));
        verify(userRepository).findUserByLogin(any());
        verify(userRepository,never()).addUser(any());
    }

    @Test
    public void Authorized_WithCorrectAndIncorrectParameters_VoidReturnedAndExceptionThrows(){
        when(userRepository.findUserByLoginAndPassword("login","password"))
                .thenReturn(Optional.of(new User("login","password")));
        when(userRepository.findUserByLoginAndPassword("correctLogin","correctPassword"))
                .thenReturn(Optional.empty());

        assertFalse(userService.authorized("correctLogin","correctPassword"));
        verify(userRepository).findUserByLoginAndPassword(any(),any());

        assertTrue(userService.authorized("login","password"));
        verify(userRepository,times(2)).findUserByLoginAndPassword(any(),any());

        assertThrows(IllegalArgumentException.class,()->userService.authorized("","password"));
        verify(userRepository,times(2)).findUserByLoginAndPassword(any(),any());

        assertThrows(IllegalArgumentException.class,()->userService.authorized("normal",""));
        verify(userRepository,times(2)).findUserByLoginAndPassword(any(),any());

        assertThrows(IllegalArgumentException.class,()->userService.authorized("",""));
        verify(userRepository,times(2)).findUserByLoginAndPassword(any(),any());

        assertThrows(IllegalArgumentException.class,()->userService.authorized(null,null));
        verify(userRepository,times(2)).findUserByLoginAndPassword(any(),any());
    }

}