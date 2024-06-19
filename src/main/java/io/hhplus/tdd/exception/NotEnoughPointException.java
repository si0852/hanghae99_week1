package io.hhplus.tdd.exception;

import io.hhplus.tdd.ErrorResponse;

/**
 * 사용하는 포인트를 차감 후 마이너스가 되었을때 발생시킬 Exception
 */
public class NotEnoughPointException extends Exception{

    private ErrorResponse errorResponse;

    public NotEnoughPointException(ErrorResponse errorResponse) {
        this.errorResponse = errorResponse;
    }

    public ErrorResponse getErrorResponse() {
        return errorResponse;
    }
}
