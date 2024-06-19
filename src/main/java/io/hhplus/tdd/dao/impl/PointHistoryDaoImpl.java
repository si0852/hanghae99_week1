package io.hhplus.tdd.dao.impl;

import io.hhplus.tdd.dao.PointHistoryDao;
import io.hhplus.tdd.database.PointHistoryTable;
import io.hhplus.tdd.point.PointHistory;
import io.hhplus.tdd.point.TransactionType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class PointHistoryDaoImpl implements PointHistoryDao {

    private PointHistoryTable pointHistoryTable;

    @Autowired
    public PointHistoryDaoImpl(PointHistoryTable pointHistoryTable) throws Exception {
        this.pointHistoryTable = pointHistoryTable;
    }


    @Override
    public PointHistory insert(long userId, long amount, TransactionType type, long updateMillis) throws Exception {
        return pointHistoryTable.insert(userId, amount, type, updateMillis);
    }

    @Override
    public List<PointHistory> selectAllByUserId(long userId) throws Exception {
        return pointHistoryTable.selectAllByUserId(userId);
    }
}
