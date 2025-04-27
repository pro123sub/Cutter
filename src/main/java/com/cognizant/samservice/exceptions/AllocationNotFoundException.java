package com.cognizant.samservice.exceptions;

public class AllocationNotFoundException extends RuntimeException {

    public AllocationNotFoundException(String message) {
        super(message);
    }

    public AllocationNotFoundException(String message, Throwable cause) {
        super(message, cause);
    }
}
