package com.example.app.exception;

import lombok.Getter;

import java.util.function.Supplier;

@Getter
public class AppException extends RuntimeException implements Supplier<AppException> {

    private static final long serialVersionUID = 8644200875450259900L;
    protected int httpCode;
    private transient ApiError error;

    public AppException(final ApiError apiError) {
        super(apiError.getDetail());
        this.error = apiError;
    }

    public AppException(final ApiError apiError, int httpCode) {
        super(apiError.getDetail());
        this.error = apiError;
        this.httpCode = httpCode;
    }

    @Override
    public AppException get() {
        return this;
    }
}
