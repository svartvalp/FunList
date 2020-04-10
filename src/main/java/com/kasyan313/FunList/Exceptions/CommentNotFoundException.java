package com.kasyan313.FunList.Exceptions;

public class CommentNotFoundException extends RuntimeException {
    public CommentNotFoundException() {
        super("comment not found");
    }

    public CommentNotFoundException(String message) {
        super(message);
    }
}
