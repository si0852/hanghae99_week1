package io.hhplus.tdd.dto;


public class UserDto {
    private long id;
    private long point;
    private long updateMillis;

    public UserDto(long id, long point, long updateMillis) {
        this.id = id;
        this.point = point;
        this.updateMillis = updateMillis;
    }

    public long getId() {
        return id;
    }

    public long getPoint() {
        return point;
    }

    public long getUpdateMillis() {
        return updateMillis;
    }
}
