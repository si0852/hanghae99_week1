package io.hhplus.tdd.service.impl;

import io.hhplus.tdd.concurrency.threadlocal.ThreadLocalConCurrencyControl;
import io.hhplus.tdd.dao.PointHistoryDao;
import io.hhplus.tdd.dao.UserPointDao;
import io.hhplus.tdd.point.PointHistory;
import io.hhplus.tdd.point.TransactionType;
import io.hhplus.tdd.point.UserPoint;
import io.hhplus.tdd.service.PointService;
import io.hhplus.tdd.validation.PointValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;


@Service
public class PointServiceImpl implements PointService {

    private UserPointDao userPointDao;
    private PointHistoryDao pointHistoryDao;
    private ThreadLocalConCurrencyControl control;

    @Autowired
    public PointServiceImpl(UserPointDao userPointDao, PointHistoryDao pointHistoryDao, ThreadLocalConCurrencyControl control) {
        this.userPointDao = userPointDao;
        this.pointHistoryDao = pointHistoryDao;
        this.control = control;
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
        try {
            // 기존 저장되어 있는 포인트 조회
            UserPoint selectPointByUser = getUserPoint(id);
            // 조회한 포인트 + 충전할 포인트
            long finalAmount = amount + selectPointByUser.point();
            // 최종 계산된 포인트와 id 저장
            return userPointDao.insertUserPoint(id, finalAmount);
        }finally {
            // 구매 히스토리 저장
            insertHistory(id, amount, TransactionType.CHARGE, System.currentTimeMillis());
        }
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
        return getUserPoint(id);
    }

    /**
     * id 별 포인트 사용하는 로직
     * @param id
     * @param amount
     * @return useUserPoint
     * @throws Exception
     */
    @Override
//    public synchronized UserPoint useUserPoint(long id, long amount) throws Exception {

    public UserPoint useUserPoint(long id, long amount) throws Exception {
        try {
            // 기존에 충전된 포인트조회
            UserPoint selectpoint = getUserPoint(id);
            // 조회한 포인트조회 - 사용할 포인트 계산
            long calAmount = selectpoint.point() - amount;
            // 계산된 포인트가 마이너스이면 에러 발생
            PointValidator.validate(PointValidator.pointOfLackValidate(calAmount));
            // 계산된 포인트 업데이트
            UserPoint userUpdatePoint = userPointDao.useUserPoint(id, calAmount);
            return userUpdatePoint;
        }finally {
            // history 저장
            insertHistory(id, amount, TransactionType.USE, System.currentTimeMillis());
        }
    }

    /**
     * 사용자별 포인트 충전/사용 내역 조회
     * @param id
     * @return List<PointHistory>
     * @throws Exception
     */
    @Override
    public List<PointHistory> selectPointHistory(long id) throws Exception {
        getUserPoint(id);
        return pointHistoryDao.selectAllByUserId(id);
    }

    private UserPoint getUserPoint(long id) throws Exception{
        PointValidator.validate(PointValidator.requestPointValidate(id));
        Optional<UserPoint> optionalUserPoint = Optional.ofNullable(userPointDao.selectPointByUserId(id));
        UserPoint userPoint = optionalUserPoint.orElseThrow(() -> new Exception("조회된 유저가 없습니다."));
        return userPoint;
    }

    private PointHistory insertHistory(long id, long amount, TransactionType type, long time) throws Exception{
        PointValidator.validate(PointValidator.requestPointValidate(amount, id));
        return pointHistoryDao.insert(id, amount, type, time);
    }
}
