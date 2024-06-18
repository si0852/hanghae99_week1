package io.hhplus.tdd.service;

import io.hhplus.tdd.point.PointHistory;
import io.hhplus.tdd.point.UserPoint;

import java.util.List;

public interface PointService {

    UserPoint insertUserPoint(long id, long amount);

    UserPoint selectUserPoint(long id);

    UserPoint useUserPoint(long id, long amount);

    List<PointHistory> selectPointHistory(long id);

}
