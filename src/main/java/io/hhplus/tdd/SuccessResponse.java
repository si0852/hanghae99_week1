package io.hhplus.tdd;

public record SuccessResponse(
        String code,
        String message,
        Object data
) {
}
