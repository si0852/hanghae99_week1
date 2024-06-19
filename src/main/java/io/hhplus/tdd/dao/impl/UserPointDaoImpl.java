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
    public UserPointDaoImpl(UserPointTable userPointRepository) throws Exception {
        this.userPointRepository = userPointRepository;
    }

    @Override
    public UserPoint insertUserPoint(long id, long amount) throws Exception {
        return userPointRepository.insertOrUpdate(id, amount);
    }

    @Override
    public UserPoint selectPointByUserId(long id) throws Exception {
        return userPointRepository.selectById(id);
    }

    @Override
    public UserPoint useUserPoint(long id, long amount) throws Exception {
        return userPointRepository.insertOrUpdate(id, amount);
    }
}
