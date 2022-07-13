package com.example.app.exception;

import com.example.app.enums.ErrorType;

public class BadRequestException extends AppException{

    public BadRequestException(ErrorType errorType, String message) {
        super(new ApiError(errorType,message));
    }
}
