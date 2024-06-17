package io.hhplus.tdd.service;

import io.hhplus.tdd.point.UserPoint;

public interface PointService {

    UserPoint insertUserPoint(long id, long amount);

    UserPoint selectUserPoint(long id);

}
