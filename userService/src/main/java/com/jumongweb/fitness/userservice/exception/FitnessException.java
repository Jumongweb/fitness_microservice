package com.jumongweb.fitness.userservice.exception;

public class FitnessException extends RuntimeException {
    public FitnessException(String emailAlreadyExist) {
        super(emailAlreadyExist);
    }
}
