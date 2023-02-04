package org.example.repository;

import org.example.model.User;

import java.util.*;

public class UserRepository {
    private final List<User> users;

    public UserRepository() {
        users = new ArrayList<>();
    }

    public Collection<User> getAllUsers() {
        return Collections.unmodifiableCollection(users);
    }

    public Optional<User> findUserByLogin(String login) {
        return users.stream().filter(user -> user.getLogin().equals(login)).findFirst();
    }

    public Optional<User> findUserByLoginAndPassword(String login, String password) {
        return users.stream()
                .filter(user ->
                        user.getLogin().equals(login) && user.getPassword().equals(password)).findFirst();
    }

    public void addUser(User user){
        users.add(user);
    }
}
