package com.example.app.exception;

import com.example.app.enums.ErrorType;

public class NotFoundException extends AppException{

    public NotFoundException(ErrorType errorType, String message) {
        super(new ApiError(errorType,message));
    }
}
