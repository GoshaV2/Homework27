package org.example.constant;

import org.example.model.User;

public enum UserConstant {
    USER1(new User("login1","password1")),
    USER2(new User("login2","password1")),
    USER3(new User("login3","password2")),
    USER4(new User("login4","password3"));
    private final User user;

    UserConstant(User user) {
        this.user = user;
    }

    public User getUser() {
        return user;
    }
}
