package io.hhplus.tdd.service;

import io.hhplus.tdd.controller.PointController;
import io.hhplus.tdd.dao.PointHistoryDao;
import io.hhplus.tdd.dao.UserPointDao;
import io.hhplus.tdd.point.PointHistory;
import io.hhplus.tdd.point.TransactionType;
import io.hhplus.tdd.point.UserPoint;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;
import java.util.UUID;

import static org.assertj.core.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
public class PointServiceTest {

    private PointService pointService;
    private PointHistoryDao historyDao;
    private static final Logger log = LoggerFactory.getLogger(PointServiceTest.class);

    @Autowired
    public PointServiceTest(PointService pointService, PointHistoryDao historyDao) {
        this.pointService = pointService;
        this.historyDao = historyDao;
    }

    @Test
    @DisplayName("첫번째 유저별 포인트 저장하는 서비스로직 : 모든 경우가 성공하는 경우")
    void insertPointService() {
        //given
        long id = 1L;
        long amount = 10L;
        //when
        UserPoint userPoint = pointService.insertUserPoint(id, amount);
        //then
        assertThat(userPoint).isEqualTo(new UserPoint(id, amount, userPoint.updateMillis()));
    }

    @Test
    @DisplayName("두번째 유저별 포인트 저장하는 서비스로직 : 충전한 유저 id와 충전양을 리턴")
    void insertPointService2() {
        //given
        long id = 1L;
        long amount = 10L;
        long millis = System.currentTimeMillis();
        //when
        UserPoint userPoint = pointService.insertUserPoint(id, amount);
        //then
        assertThat(userPoint).isEqualTo(new UserPoint(id, amount, userPoint.updateMillis()));
    }

    @Test
    @DisplayName("세번째 유저별 포인트 저장하는 서비스로직: dao, repository 연동하기")
    void insertPointServiceConnectRepository() {
        //given
        long id = 1L;
        long amount = 10L;
        //when
        UserPoint userPoint = pointService.insertUserPoint(id, amount);
        //then
        assertThat(userPoint).isEqualTo(new UserPoint(id, amount, userPoint.updateMillis()));
    }

    @Test
    @DisplayName("네번째 유저별 포인트 저장하는 서비스로직: 저장된 유저별 포인트와 조회하는 로직을 구현하여 맞는지 비교")
    void insertPointServiceCreateRandomUUID() {
        //given
        long id = Math.abs(UUID.randomUUID().getLeastSignificantBits());
        long amount = 10L;
        //when
        UserPoint userPoint = pointService.insertUserPoint(id, amount);
        //then
        UserPoint selectPoint = pointService.selectUserPoint(id);
        assertThat(userPoint).isEqualTo(selectPoint);
    }

    @Test
    @DisplayName("다섯번째 유저별 포인트 저장하는 서비스로직: history 저장 Test(무조건 성공)")
    void insertPointServiceCreateWithHistory() {
        //given
        long id = Math.abs(UUID.randomUUID().getLeastSignificantBits());
        long amount = 10L;
        TransactionType type = TransactionType.CHARGE;
        long updateMillis = System.currentTimeMillis();
        //when
        PointHistory insertPointHistory = historyDao.insert(id, amount, type, updateMillis);
        //then
        assertThat(insertPointHistory).isNull();
    }

    @Test
    @DisplayName("여섯번째 유저별 포인트 저장하는 서비스로직: history 저장 Test, 저장되는 정보 확인하기")
    void insertPointServiceCreateWithInsertDataConfirm() {
        //given
        long id = Math.abs(UUID.randomUUID().getLeastSignificantBits());
        long amount = 10L;
        TransactionType type = TransactionType.CHARGE;
        long updateMillis = System.currentTimeMillis();
        PointHistory history = new PointHistory(1,id, amount, type, updateMillis);
        //when
        PointHistory insertPointHistory = historyDao.insert(id, amount, type, updateMillis);
        //then
        assertThat(insertPointHistory).isEqualTo(history);
    }

    // 실패케이스작성?
    @Test
    @DisplayName("일곱번째 유저별 포인트 저장하는 서비스로직: history 저장 로직추가 그리고 확인하기")
    void addHistoryInsertLogicinServiceLogic() {
        //given
        long id = Math.abs(UUID.randomUUID().getLeastSignificantBits());
        long amount = 10L;
        //when
        UserPoint userPoint = pointService.insertUserPoint(id, amount);
        List<PointHistory> histories = historyDao.selectAllByUserId(userPoint.id());
        //then
        assertEquals(1, histories.size());
    }

    @Test
    @DisplayName("일곱번째 유저별 포인트 저장하는 서비스로직: history 저장 로직추가 그리고 확인하기2")
    void addHistoryInsertLogicinServiceLogic2() {
        //given
        long id = Math.abs(UUID.randomUUID().getLeastSignificantBits());
        long id2 = 11111L;
        long amount = 10L;
        //when
        pointService.insertUserPoint(id, amount);
        pointService.insertUserPoint(id, amount);
        pointService.insertUserPoint(id, amount);
        pointService.insertUserPoint(id, amount);
        pointService.insertUserPoint(id2, amount);

        List<PointHistory> histories = historyDao.selectAllByUserId(id);
        log.info("histories size: " + histories.size());
        //then
        assertEquals(4, histories.size());
    }



}
