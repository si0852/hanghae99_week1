package io.hhplus.tdd.dao;

import io.hhplus.tdd.point.PointHistory;
import io.hhplus.tdd.point.TransactionType;

import java.util.List;

public interface PointHistoryDao {

    PointHistory insert(long userId, long amount, TransactionType type, long updateMillis) throws Exception;

    List<PointHistory> selectAllByUserId(long userId) throws Exception;
}
