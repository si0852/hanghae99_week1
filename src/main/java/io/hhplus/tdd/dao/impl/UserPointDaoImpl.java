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
        return userPointRepository.insertOrUpdate(id, amount);
    }
}
