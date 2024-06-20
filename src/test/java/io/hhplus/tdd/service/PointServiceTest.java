package io.hhplus.tdd.service;

import io.hhplus.tdd.dao.PointHistoryDao;
import io.hhplus.tdd.exception.BadRequestPointException;
import io.hhplus.tdd.exception.NotEnoughPointException;
import io.hhplus.tdd.point.PointHistory;
import io.hhplus.tdd.point.TransactionType;
import io.hhplus.tdd.point.UserPoint;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;
import java.util.UUID;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

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

    @BeforeEach
    void setUp() throws Exception{
        pointService.insertUserPoint(1L, 1000L);
        pointService.insertUserPoint(2L, 2000L);
    }

    @Test
    @DisplayName("동시 포인트 요청 Test : synchronized")
    void usePointReqeustAtTheSameTime() throws Exception{
        //given
        final int threadCount = 100;
        final ExecutorService executorService = Executors.newFixedThreadPool(32);
        final CountDownLatch countDownLatch = new CountDownLatch(threadCount);

        //when
        for (int i = 0; i < threadCount; i++) {
            executorService.submit(() -> {
                try {
                    sleep(500);
                    pointService.useUserPoint(1L, 10L);
                } catch (Exception e) {
                    log.info("message: " + e.getMessage());
                }finally {
                    countDownLatch.countDown();
                }
            });
        }
        countDownLatch.await();
        UserPoint userPoint = pointService.selectUserPoint(1L);
        assertThat(userPoint.point()).isEqualTo(0);
    }

    private void sleep(int millis) {
        try {
            Thread.sleep(millis);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    @Test
    @DisplayName("첫번째 유저별 포인트 저장하는 서비스로직 : 모든 경우가 성공하는 경우")
    void insertPointService() throws Exception {
        //given
        long id = Math.abs(UUID.randomUUID().getLeastSignificantBits());
        long amount = 10L;
        //when
        UserPoint userPoint = pointService.insertUserPoint(id, amount);
        //then
        assertThat(userPoint).isEqualTo(new UserPoint(id, amount, userPoint.updateMillis()));
    }

    @Test
    @DisplayName("두번째 유저별 포인트 저장하는 서비스로직 : 충전한 유저 id와 충전양을 리턴")
    void insertPointService2() throws Exception {
        //given
        long id = Math.abs(UUID.randomUUID().getLeastSignificantBits());
        long amount = 10L;
        //when
        UserPoint beforeUserPoint = pointService.selectUserPoint(amount);
        UserPoint userPoint = pointService.insertUserPoint(id, amount);
        //then
        assertThat(beforeUserPoint.point()+amount).isEqualTo(userPoint.point());
    }

    @Test
    @DisplayName("세번째 유저별 포인트 저장하는 서비스로직: dao, repository 연동하기")
    void insertPointServiceConnectRepository() throws Exception {
        //given
        long id = Math.abs(UUID.randomUUID().getLeastSignificantBits());
        long amount = 10L;
        //when
        UserPoint userPoint = pointService.insertUserPoint(id, amount);
        //then
        assertThat(userPoint).isEqualTo(new UserPoint(id, amount, userPoint.updateMillis()));
    }

    @Test
    @DisplayName("네번째 유저별 포인트 저장하는 서비스로직: 저장된 유저별 포인트와 조회하는 로직을 구현하여 맞는지 비교")
    void insertPointServiceCreateRandomUUID() throws Exception {
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
    void insertPointServiceCreateWithHistory() throws Exception {
        //given
        long id = Math.abs(UUID.randomUUID().getLeastSignificantBits());
        long amount = 10L;
        TransactionType type = TransactionType.CHARGE;
        long updateMillis = System.currentTimeMillis();
        //when
        PointHistory insertPointHistory = historyDao.insert(id, amount, type, updateMillis);
        //then
        assertThat(insertPointHistory).isNotNull();
    }

    @Test
    @DisplayName("여섯번째 유저별 포인트 저장하는 서비스로직: history 저장 Test, 저장되는 정보 확인하기")
    void insertPointServiceCreateWithInsertDataConfirm() throws Exception {
        //given
        long id = Math.abs(UUID.randomUUID().getLeastSignificantBits());
        long amount = 10L;
        TransactionType type = TransactionType.CHARGE;
        long updateMillis = System.currentTimeMillis();
        //when
        PointHistory insertPointHistory = historyDao.insert(id, amount, type, updateMillis);
        List<PointHistory> history = historyDao.selectAllByUserId(id);
        //then
        assertEquals(1, history.size());
        assertThat(insertPointHistory).isEqualTo(history.get(0));
    }

    // 실패케이스작성?
    @Test
    @DisplayName("일곱번째 유저별 포인트 저장하는 서비스로직: history 저장 로직추가 그리고 확인하기")
    void addHistoryInsertLogicinServiceLogic() throws Exception {
        //given
        long id = Math.abs(UUID.randomUUID().getLeastSignificantBits());
        long amount = 10L;
        //when
        UserPoint userPoint = pointService.insertUserPoint(id, amount);
        List<PointHistory> histories = historyDao.selectAllByUserId(userPoint.id()).stream().filter(history -> history.type().equals(TransactionType.CHARGE)).toList();
        //then
        assertEquals(1, histories.size());
    }

    @Test
    @DisplayName("일곱번째 유저별 포인트 저장하는 서비스로직: history 저장 로직추가 그리고 확인하기2")
    void addHistoryInsertLogicinServiceLogic2() throws Exception {
        //given
        long id = Math.abs(UUID.randomUUID().getLeastSignificantBits());
        long id2 = 1232322L;
        long amount = 10L;
        //when
        pointService.insertUserPoint(id, amount);
        pointService.insertUserPoint(id, amount);
        pointService.insertUserPoint(id, amount);
        pointService.insertUserPoint(id, amount);
        pointService.insertUserPoint(id2, amount);

        List<PointHistory> histories = historyDao.selectAllByUserId(id).stream().filter(history -> history.type().equals(TransactionType.CHARGE)).toList();
        log.info("histories size: " + histories.size());
        //then
        assertEquals(4, histories.size());
    }

    @Test
    @DisplayName("여덟번쨰 유저별 포인트 저장하는 서비스로직: 충전시 기존에 있던 금액을 조회한 결과 포인트를 더해서 저장해야 한다.")
    void pointChargeServiceLogicForCalculate() throws Exception {
        //given
        long id = Math.abs(UUID.randomUUID().getLeastSignificantBits());
        long amount = 1000L;
        long amount2 = 1200l;

        //when
        pointService.insertUserPoint(id, amount);
        pointService.insertUserPoint(id, amount2);
        long totalAmount = pointService.selectUserPoint(id).point();

        //then
        assertEquals(amount+amount2, totalAmount);

    }

    /**
     * 포인트를 사용하는 서비스 로직
     */
    @Test
    @DisplayName("첫번째 유저별 포인트를 사용하는 서비스 로직: 사용했을 경우 무조건 성공 케이스")
    void usePointByUserandSuccessCase() throws Exception {
        //given
        long id = Math.abs(UUID.randomUUID().getLeastSignificantBits());
        long amount = 1000L;
        pointService.insertUserPoint(id, amount + amount);
        //when
        UserPoint userPoint = pointService.useUserPoint(id, amount);
        //then
        assertThat(userPoint).isNotNull();
    }

    @Test
    @DisplayName("두번째 유저별 포인트를 사용하는 서비스 로직: 포인트를 사용하기 전 금액 조회하기")
    void selectPointforUsePoint()  throws Exception{
        //given
        long id = Math.abs(UUID.randomUUID().getLeastSignificantBits());
        long amount = 1000L;
        long amount2 = 1200l;
        pointService.insertUserPoint(id, amount);
        pointService.insertUserPoint(id, amount2);

        //when
        UserPoint selectPointUser = pointService.useUserPoint(id,amount);

        //then
        assertEquals(amount2, selectPointUser.point());
    }

    @Test
    @DisplayName("세번째 유저별 포인트를 사용하는 서비스 로직: 조회한 포인트 - 사용하는 포인트 계산하기")
    void calculatePoint()  throws Exception{
        //given
        long id = Math.abs(UUID.randomUUID().getLeastSignificantBits());
        long amount = 1000L;
        long amount2 = 1200l;
        pointService.insertUserPoint(id, amount);
        pointService.insertUserPoint(id, amount2);
        long usePoint = 1000L;

        //when
        UserPoint useUserPoint = pointService.useUserPoint(id, usePoint);

        //then
        long calPoint = (amount2 + amount) - usePoint;
        assertEquals(calPoint, useUserPoint.point());
    }

    @Test
    @DisplayName("네번째 유저별 포인트를 사용하는 서비스 로직: 계산된 포인트가 < 0 일 경우")
    void calculatePointMinus() throws Exception{
        //given
        long id = Math.abs(UUID.randomUUID().getLeastSignificantBits());
        long amount = 1000L;
        long amount2 = 1200l;
        long usePoint = 3000L;

        //when
        pointService.insertUserPoint(id, amount);
        pointService.insertUserPoint(id, amount2);

        //then
        assertThrows(NotEnoughPointException.class, () -> {pointService.useUserPoint(id, usePoint);});
    }

    @Test
    @DisplayName("다섯번째 유저별 포인트를 사용하는 서비스 로직: 사용 내역 history 테이블 추가")
    void useServiceLogicWithHistory()  throws Exception{
        //given
        long id = Math.abs(UUID.randomUUID().getLeastSignificantBits());
        long amount = 1000L;
        long amount2 = 1200l;
        pointService.insertUserPoint(id, amount);
        pointService.insertUserPoint(id, amount2);
        long usePoint = 2000L;

        //when
        pointService.useUserPoint(id, usePoint);
        List<PointHistory> userHistory = historyDao.selectAllByUserId(id).stream().filter(history -> history.type().equals(TransactionType.USE)).toList();

        //then
        assertEquals(1, userHistory.size());
    }

    @Test
    @DisplayName("포인트 내역 조회 서비스 로직: 빈 리스트 리턴")
    void selectPointHistoryandReturnEmptyList() throws Exception {
        //given
        long id = Math.abs(UUID.randomUUID().getLeastSignificantBits());

        //when
        List<PointHistory> selectHistory = pointService.selectPointHistory(id);

        //then
        assertEquals(0, selectHistory.size());
    }

    @Test
    @DisplayName("포인트 내역 조회 서비스 로직: 내역조회")
    void selectPointHistory() throws Exception {
        //given
        long id = Math.abs(UUID.randomUUID().getLeastSignificantBits());
        long amount = 1000L;
        long amount2 = 1200l;
        long usePoint = 2000L;
        pointService.insertUserPoint(id, amount);
        pointService.insertUserPoint(id, amount2);
        pointService.useUserPoint(id, usePoint);

        //when
        List<PointHistory> selectHistory = pointService.selectPointHistory(id);
        log.info("selectHistory : " + selectHistory.toString());
        //then
        assertEquals(3, selectHistory.size());
    }

    @Test
    @DisplayName("요청준 point에 대한 테스트 진행: 0보다 작을떄")
    void requestPointTest() {
        //given
        long id = 1;
        long amount = -1;
        //when
        //then
        assertThrows(BadRequestPointException.class, () -> pointService.insertUserPoint(id, amount));
    }


}
