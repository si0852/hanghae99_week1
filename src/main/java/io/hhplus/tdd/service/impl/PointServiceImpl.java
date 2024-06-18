package io.hhplus.tdd.service.impl;

import io.hhplus.tdd.dao.PointHistoryDao;
import io.hhplus.tdd.dao.UserPointDao;
import io.hhplus.tdd.point.PointHistory;
import io.hhplus.tdd.point.TransactionType;
import io.hhplus.tdd.point.UserPoint;
import io.hhplus.tdd.service.PointService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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
    public UserPoint insertUserPoint(long id, long amount) {
        try {
            UserPoint selectPointByUser = userPointDao.selectPointByUserId(id);
            pointHistoryDao.insert(id, amount, TransactionType.CHARGE, System.currentTimeMillis());
            long finalAmount = amount + selectPointByUser.point();
            return userPointDao.insertUserPoint(id,finalAmount);
        } catch (Exception e) {
            return null;
        }
    }

    @Override
    public UserPoint selectUserPoint(long id) {
        try {
            List<PointHistory> historyPoint = pointHistoryDao.selectAllByUserId(id);
            long totalPoint = historyPoint.stream().mapToLong(PointHistory::amount).sum();
//            return userPointDao.selectPointByUserId(id);
            return new UserPoint(id, totalPoint, 0);
        } catch (Exception e) {
            return null;
        }
    }

    @Override
    public UserPoint useUserPoint(long id, long amount) {
        try {
            UserPoint selectpoint = userPointDao.selectPointByUserId(id);
            long  calAmount = selectpoint.point() - amount;
            if(calAmount < 0) {
                return null;
            }
            pointHistoryDao.insert(id, amount, TransactionType.USE, System.currentTimeMillis());
            return userPointDao.useUserPoint(id, calAmount);
        } catch (Exception e) {
            return null;
        }
    }
}
