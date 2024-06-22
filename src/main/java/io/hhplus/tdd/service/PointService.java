package io.hhplus.tdd.service;

import io.hhplus.tdd.point.PointHistory;
import io.hhplus.tdd.point.UserPoint;

import java.util.List;

public interface PointService {

    UserPoint insertUserPoint(long id, long amount) throws Exception ;

    UserPoint selectUserPoint(long id) throws Exception ;

    UserPoint useUserPoint(long id, long amount) throws Exception;

    List<PointHistory> selectPointHistory(long id) throws Exception ;

}
