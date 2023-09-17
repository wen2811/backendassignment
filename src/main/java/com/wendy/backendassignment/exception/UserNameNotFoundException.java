package com.wendy.backendassignment.exception;

public class UserNameNotFoundException extends RuntimeException {
    private static final long serialVersionUID = 1L;

    public UserNameNotFoundException(String username) {
        super("So sorry, can not find user " + username);
    }
}
