package io.hhplus.tdd.dao;

import io.hhplus.tdd.point.UserPoint;

public interface UserPointDao {
    UserPoint insertUserPoint(long id, long amount) throws Exception;

    UserPoint selectPointByUserId(long id) throws Exception;

    UserPoint useUserPoint(long id, long amount) throws Exception;
}
