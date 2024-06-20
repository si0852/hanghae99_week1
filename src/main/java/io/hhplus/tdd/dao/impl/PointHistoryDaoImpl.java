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
    public PointHistoryDaoImpl(PointHistoryTable pointHistoryTable){
        this.pointHistoryTable = pointHistoryTable;
    }


    /**
     * 포인트 충전/사용 이력 저장
     * @param userId
     * @param amount
     * @param type
     * @param updateMillis
     * @return
     * @throws Exception
     */
    @Override
    public PointHistory insert(long userId, long amount, TransactionType type, long updateMillis){
        return pointHistoryTable.insert(userId, amount, type, updateMillis);
    }

    /**
     * 유저별 포인트 충전/사용 내역 조회
     * @param userId
     * @return
     * @throws Exception
     */
    @Override
    public List<PointHistory> selectAllByUserId(long userId){
        return pointHistoryTable.selectAllByUserId(userId);
    }
}
