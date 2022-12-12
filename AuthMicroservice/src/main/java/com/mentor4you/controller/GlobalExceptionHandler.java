package com.mentor4you.controller;

import com.mentor4you.exception.EntityNotFoundException;
import com.mentor4you.exception.LoginException;
import com.mentor4you.exception.RegistrationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class GlobalExceptionHandler {

  @ExceptionHandler({EntityNotFoundException.class})
  public ResponseEntity<?> handleEntityNotFoundException(EntityNotFoundException ex) {
    return ResponseEntity.status(HttpStatus.NOT_FOUND).body(ex.getMessage());
  }

  @ExceptionHandler({RegistrationException.class, LoginException.class})
  public ResponseEntity<?> handleAuthenticationExceptions(RuntimeException ex) {
    return ResponseEntity.badRequest().body(ex.getMessage());
  }
}
