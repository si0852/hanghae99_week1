package io.hhplus.tdd.dao.impl;

import io.hhplus.tdd.concurrency.ConCurrencyStatus;
import io.hhplus.tdd.concurrency.threadlocal.ConCurrencyControl;
import io.hhplus.tdd.dao.UserPointDao;
import io.hhplus.tdd.database.UserPointTable;
import io.hhplus.tdd.point.UserPoint;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class UserPointDaoImpl implements UserPointDao {

    private UserPointTable userPointRepository;
    private ConCurrencyControl control;

    @Autowired
    public UserPointDaoImpl(UserPointTable userPointRepository, ConCurrencyControl control) throws Exception {
        this.userPointRepository = userPointRepository;
        this.control = control;
    }

    /**
     * 유저별 포인트 충전
     * @param id
     * @param amount
     * @return
     * @throws Exception
     */
    @Override
    public UserPoint insertUserPoint(long id, long amount) throws Exception {
        return userPointRepository.insertOrUpdate(id, amount);
    }

    /**
     * 유저별 포인트 조회
     * @param id
     * @return
     * @throws Exception
     */
    @Override
    public UserPoint selectPointByUserId(long id) throws Exception {
        return userPointRepository.selectById(id);
    }

    /**
     * 사용한 포인트 만큼 차감 후 업데이트
     * @param id
     * @param amount
     * @return
     * @throws Exception
     */
    @Override
    public UserPoint useUserPoint(long id, long amount) throws Exception {
        ConCurrencyStatus status = control.begin();
        sleep(500);
        UserPoint userResultPoint = userPointRepository.insertOrUpdate(id, amount);
        control.end(status);
        return userResultPoint;
    }

    private void sleep(int millis) {
        try {
            Thread.sleep(millis);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
