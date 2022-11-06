package com.mentor4you.exception;

public class MenteeNotFoundException extends RuntimeException{

    public MenteeNotFoundException(String message){
        super(message);
    }
}
