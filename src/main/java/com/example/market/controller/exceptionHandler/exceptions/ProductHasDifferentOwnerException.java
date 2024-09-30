package com.example.market.controller.exceptionHandler.exceptions;

public class ProductHasDifferentOwnerException extends RuntimeException{
    public ProductHasDifferentOwnerException(String message) {
        super(message);
    }
}
