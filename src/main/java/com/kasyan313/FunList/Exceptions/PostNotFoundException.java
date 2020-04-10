package com.kasyan313.FunList.Exceptions;

public class PostNotFoundException extends RuntimeException {
    public PostNotFoundException() {
        super("post not found");
    }

    public PostNotFoundException(String message) {
        super(message);
    }
}
