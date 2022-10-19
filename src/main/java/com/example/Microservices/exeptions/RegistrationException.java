package com.example.Microservices.exeptions;

public class RegistrationException extends RuntimeException{

    public RegistrationException(String message) {
        super(message);
    }
}
