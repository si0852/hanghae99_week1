package io.hhplus.tdd.service.impl;

import io.hhplus.tdd.point.UserPoint;
import io.hhplus.tdd.service.PointService;
import org.springframework.stereotype.Service;

@Service
public class PointServiceImpl implements PointService {
    @Override
    public UserPoint insertUserPoint(long id, long amount, long millis) {
        return new UserPoint(id,amount,millis);
    }
}