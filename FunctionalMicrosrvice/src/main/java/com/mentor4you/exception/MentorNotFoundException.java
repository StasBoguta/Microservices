package com.mentor4you.exception;

public class MentorNotFoundException extends RuntimeException{

    public MentorNotFoundException(String message){
        super(message);
    }

    public static class UnkownIdentifierException {
    }
}
