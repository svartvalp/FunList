package com.kasyan313.FunList.Controllers;

import com.kasyan313.FunList.Exceptions.CommentNotFoundException;
import com.kasyan313.FunList.Exceptions.PostNotFoundException;
import com.kasyan313.FunList.Exceptions.UserNotFoundException;
import com.kasyan313.FunList.Exceptions.ValidationFailedException;
import com.kasyan313.FunList.MessageEntities.ErrorMessage;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class ExceptionController {
    @ExceptionHandler(value = {UserNotFoundException.class, PostNotFoundException.class, ValidationFailedException.class,
            CommentNotFoundException.class})
    public ResponseEntity<ErrorMessage> handleError(Exception ex) {
        return ResponseEntity.status(400).body(new ErrorMessage(ex.getMessage()));
    }
}
