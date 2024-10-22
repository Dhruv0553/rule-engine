package com.zeotap.ruleengine.exception;

// Custom exception class for handling invalid rule syntax or empty rule cases
public class RuleValidationException extends RuntimeException {

    // Constructor that accepts a custom error message when the exception is thrown
    public RuleValidationException(String message) {
        super(message);  // Passes the message to the RuntimeException class
    }
}

