package io.hhplus.tdd.service.impl;

import io.hhplus.tdd.dao.UserPointDao;
import io.hhplus.tdd.point.UserPoint;
import io.hhplus.tdd.service.PointService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class PointServiceImpl implements PointService {

    private UserPointDao userPointDao;

    @Autowired
    public PointServiceImpl(UserPointDao userPointDao) {
        this.userPointDao = userPointDao;
    }


    @Override
    public UserPoint insertUserPoint(long id, long amount, long millis) {
        return userPointDao.insertUserPoint(id,amount, millis);
    }
}
