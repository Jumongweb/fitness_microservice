package com.jumongweb.fitness.userservice.exception;

public class UserNotFoundException extends FitnessException{
    public UserNotFoundException(String emailAlreadyExist) {
        super(emailAlreadyExist);
    }
}
