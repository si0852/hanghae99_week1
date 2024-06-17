package io.hhplus.tdd.dao.impl;

import io.hhplus.tdd.dao.UserPointDao;
import io.hhplus.tdd.database.UserPointTable;
import io.hhplus.tdd.point.UserPoint;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class UserPointDaoImpl implements UserPointDao {

    private UserPointTable userPointRepository;

    @Autowired
    public UserPointDaoImpl(UserPointTable userPointRepository) {
        this.userPointRepository = userPointRepository;
    }

    @Override
    public UserPoint insertUserPoint(long id, long amount, long millis) {
        return new UserPoint(id, amount, System.currentTimeMillis());
    }
}
