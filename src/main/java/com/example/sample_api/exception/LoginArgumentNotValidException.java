package com.example.sample_api.exception;

public class LoginArgumentNotValidException extends RuntimeException{
    public LoginArgumentNotValidException(String message) {
        super(message);
    }
}