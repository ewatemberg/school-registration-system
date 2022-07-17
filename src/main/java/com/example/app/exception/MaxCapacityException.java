package com.example.app.exception;

import com.example.app.enums.ErrorType;

public class MaxCapacityException extends AppException{

    public MaxCapacityException(ErrorType errorType, String message) {
        super(new ApiError(errorType,message));
    }
}
