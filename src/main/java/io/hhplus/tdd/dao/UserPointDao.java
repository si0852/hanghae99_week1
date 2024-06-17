package io.hhplus.tdd.dao;

import io.hhplus.tdd.point.UserPoint;

public interface UserPointDao {
    UserPoint insertUserPoint(long id, long amount, long millis);
}
