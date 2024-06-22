package io.hhplus.tdd.exception;

import io.hhplus.tdd.ErrorResponse;

/**
 * 요청한 충전/사용 포인트가 마이너스일때 발생시킬 Exception
 */
public class BadRequestPointException extends Exception{

    private ErrorResponse errorResponse;

    public BadRequestPointException(ErrorResponse errorResponse) {
        this.errorResponse = errorResponse;
    }

    public ErrorResponse getErrorResponse() {
        return errorResponse;
    }
}
