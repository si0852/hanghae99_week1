package io.hhplus.tdd.exception;

import io.hhplus.tdd.ErrorResponse;

public class NotEnoughPointException extends Exception{

    private ErrorResponse errorResponse;

    public NotEnoughPointException(ErrorResponse errorResponse) {
        this.errorResponse = errorResponse;
    }

    public ErrorResponse getErrorResponse() {
        return errorResponse;
    }
}
