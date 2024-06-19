package io.hhplus.tdd.service.impl;

import io.hhplus.tdd.ErrorResponse;
import io.hhplus.tdd.dao.PointHistoryDao;
import io.hhplus.tdd.dao.UserPointDao;
import io.hhplus.tdd.exception.NotEnoughPointException;
import io.hhplus.tdd.point.PointHistory;
import io.hhplus.tdd.point.TransactionType;
import io.hhplus.tdd.point.UserPoint;
import io.hhplus.tdd.service.PointService;
import io.hhplus.tdd.validation.PointValidator;
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


    /**
     * id별 포인트를 충전
     * @param id
     * @param amount
     * @return UserPoint
     * @throws Exception
     * @Question Transaction은 어떻게 처리할까?
     */
    @Override
    public UserPoint insertUserPoint(long id, long amount) throws Exception {
        // 요청준 point에 대한 validation
        PointValidator.validate(PointValidator.requestPointValidate(amount));
        // 기존 저장되어 있는 포인트 조회
        UserPoint selectPointByUser = userPointDao.selectPointByUserId(id);
        // 구매 히스토리 저장
        pointHistoryDao.insert(id, amount, TransactionType.CHARGE, System.currentTimeMillis());
        // 조회한 포인트 + 충전할 포인트
        long finalAmount = amount + selectPointByUser.point();
        // 최종 계산된 포인트와 id 저장
        return userPointDao.insertUserPoint(id,finalAmount);
    }

    /**
     * id별 포인트 조회 로직
     * @param id
     * @return UserPoint
     * @throws Exception
     * @Question Transaction은 어떻게 처리할까?
     */
    @Override
    public UserPoint selectUserPoint(long id) throws Exception {
        return userPointDao.selectPointByUserId(id);
    }

    /**
     * id 별 포인트 사용하는 로직
     * @param id
     * @param amount
     * @return useUserPoint
     * @throws Exception
     */
    @Override
    public UserPoint useUserPoint(long id, long amount) throws Exception {
        // 요청준 point에 대한 validation
        PointValidator.validate(PointValidator.requestPointValidate(amount));
        // 기존에 충전된 포인트조회
        UserPoint selectpoint = userPointDao.selectPointByUserId(id);
        // 조회한 포인트조회 - 사용할 포인트 계산
        long  calAmount = selectpoint.point() - amount;
        // 계산된 포인트가 마이너스이면 에러 발생
        PointValidator.validate(PointValidator.pointOfLackValidate(calAmount));
        // history 저장
        pointHistoryDao.insert(id, amount, TransactionType.USE, System.currentTimeMillis());
        // 계산된 포인트 업데이트
        return userPointDao.useUserPoint(id, calAmount);
    }

    /**
     * 사용자별 포인트 충전/사용 내역 조회
     * @param id
     * @return List<PointHistory>
     * @throws Exception
     */
    @Override
    public List<PointHistory> selectPointHistory(long id) throws Exception {
        return pointHistoryDao.selectAllByUserId(id);
    }
}
