package org.example.repository;

import org.example.constant.UserConstant;
import org.example.model.User;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.*;
import java.util.stream.Collectors;

import static org.junit.jupiter.api.Assertions.*;

class UserRepositoryTest {

    private UserRepository userRepository;

    @BeforeEach
    public void initUserRepository() {
        userRepository = new UserRepository();
    }

    private void addAnyUsers() {
        userRepository.addUser(UserConstant.USER1.getUser());
        userRepository.addUser(UserConstant.USER2.getUser());
        userRepository.addUser(UserConstant.USER3.getUser());
        userRepository.addUser(UserConstant.USER4.getUser());
    }

    @Test
    void getAllUsers_WhenEmptyRepository_EmptyListReturned() {
        assertTrue(userRepository.getAllUsers().isEmpty());
    }

    @Test
    void getAllUsers_WithNoEmptyRepository_ListWithCurrentUsersReturned() {
        addAnyUsers();
        Collection<User> userCollectionFromRepository = userRepository.getAllUsers();
        Collection<User> userCollectionFromConstant = Arrays.stream(UserConstant.values())
                .map(UserConstant::getUser).collect(Collectors.toList());

        assertEquals(userCollectionFromRepository.size(), userCollectionFromConstant.size());
        assertTrue(userCollectionFromRepository.containsAll(userCollectionFromConstant));
    }

    @Test
    void findUserByLogin_WhenExistsUserAndNotExistsUser_UserReturnedAndNull() {
        addAnyUsers();
        Optional<User> user2=userRepository.findUserByLogin(UserConstant.USER2.getUser().getLogin());
        Optional<User> userNotExists=userRepository.findUserByLogin("notExists");

        assertTrue(user2.isPresent());
        assertEquals(user2.get(), UserConstant.USER2.getUser());

        assertFalse(userNotExists.isPresent());
    }

    @Test
    void findUserByLoginAndPasswordWhenExistsUserAndNotExistsUsers_UserReturned_AndNulls() {
        addAnyUsers();
        User userExists=UserConstant.USER1.getUser();
        Optional<User> userExistsOptional=userRepository.findUserByLoginAndPassword(
                userExists.getLogin(), userExists.getPassword());
        Optional<User> userNotExists1=userRepository.findUserByLoginAndPassword("test",userExists.getPassword());
        Optional<User> userNotExists2=userRepository.findUserByLoginAndPassword(userExists.getLogin(),"123");

        assertTrue(userExistsOptional.isPresent());
        assertEquals(userExistsOptional.get(),userExists);

        assertFalse(userNotExists1.isPresent());

        assertFalse(userNotExists2.isPresent());
;    }
}