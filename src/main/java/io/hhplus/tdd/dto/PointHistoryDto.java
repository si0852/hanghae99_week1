package io.hhplus.tdd.dto;

import io.hhplus.tdd.point.TransactionType;

public class PointHistoryDto {
    long id;
    long userId;
    long amount;
    TransactionType type;
    long updateMillis;

    public PointHistoryDto(long id, long userId, long amount, TransactionType type, long updateMillis) {
        this.id = id;
        this.userId = userId;
        this.amount = amount;
        this.type = type;
        this.updateMillis = updateMillis;
    }

    public long getId() {
        return id;
    }

    public long getUserId() {
        return userId;
    }

    public long getAmount() {
        return amount;
    }

    public TransactionType getType() {
        return type;
    }

    public long getUpdateMillis() {
        return updateMillis;
    }
}
