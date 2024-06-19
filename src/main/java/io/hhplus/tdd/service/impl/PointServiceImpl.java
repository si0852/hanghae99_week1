package io.hhplus.tdd.service.impl;

import io.hhplus.tdd.ErrorResponse;
import io.hhplus.tdd.dao.PointHistoryDao;
import io.hhplus.tdd.dao.UserPointDao;
import io.hhplus.tdd.exception.NotEnoughPointException;
import io.hhplus.tdd.point.PointHistory;
import io.hhplus.tdd.point.TransactionType;
import io.hhplus.tdd.point.UserPoint;
import io.hhplus.tdd.service.PointService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;


@Service
public class PointServiceImpl implements PointService {

    private UserPointDao userPointDao;
    private PointHistoryDao pointHistoryDao;

    @Autowired
    public PointServiceImpl(UserPointDao userPointDao, PointHistoryDao pointHistoryDao) {
        this.userPointDao = userPointDao;
        this.pointHistoryDao = pointHistoryDao;
    }


    @Override
    public UserPoint insertUserPoint(long id, long amount) throws Exception {
            UserPoint selectPointByUser = userPointDao.selectPointByUserId(id);
            pointHistoryDao.insert(id, amount, TransactionType.CHARGE, System.currentTimeMillis());
            long finalAmount = amount + selectPointByUser.point();
            return userPointDao.insertUserPoint(id,finalAmount);
    }

    @Override
    public UserPoint selectUserPoint(long id) throws Exception {
            return userPointDao.selectPointByUserId(id);
    }

    @Override
    public UserPoint useUserPoint(long id, long amount) throws Exception {
            UserPoint selectpoint = userPointDao.selectPointByUserId(id);
            long  calAmount = selectpoint.point() - amount;
            if(calAmount < 0) {
                 throw new NotEnoughPointException(new ErrorResponse("500", "포인트가 부족합니다."));
            }
            pointHistoryDao.insert(id, amount, TransactionType.USE, System.currentTimeMillis());
            return userPointDao.useUserPoint(id, calAmount);
    }

    @Override
    public List<PointHistory> selectPointHistory(long id) throws Exception {
        return pointHistoryDao.selectAllByUserId(id);
    }
}
