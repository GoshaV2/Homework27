package org.example.service;

import org.example.exception.UserNonUniqueException;
import org.example.model.User;
import org.example.repository.UserRepository;

import java.util.List;
import java.util.stream.Collectors;

public class UserService {
    private final UserRepository userRepository;

    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public List<String> getAllLogin() {
        return userRepository.getAllUsers().stream().map(User::getLogin).collect(Collectors.toList());
    }

    public void addNewUser(String login, String password){
        if(login == null || login.isEmpty() || password==null || password.isEmpty()){
            throw  new IllegalArgumentException();
        }
        if (userRepository.findUserByLogin(login).isPresent()) {
            throw new UserNonUniqueException();
        }
        userRepository.addUser(new User(login,password));
    }

    public boolean authorized(String login,String password){
        if(login == null || login.isEmpty() || password==null || password.isEmpty()){
            throw  new IllegalArgumentException();
        }
        return userRepository.findUserByLoginAndPassword(login,password).isPresent();
    }
}
