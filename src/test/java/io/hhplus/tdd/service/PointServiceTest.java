package io.hhplus.tdd.service;

import io.hhplus.tdd.dao.UserPointDao;
import io.hhplus.tdd.point.UserPoint;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.UUID;

@SpringBootTest
public class PointServiceTest {

    private PointService pointService;

    @Autowired
    public PointServiceTest(PointService pointService) {
        this.pointService = pointService;
    }

    @Test
    @DisplayName("첫번째 유저별 포인트 저장하는 서비스로직 : 모든 경우가 성공하는 경우")
    void insert_point_service() {
        //given
        long id = 1L;
        long amount = 10L;
        //when
        UserPoint userPoint = pointService.insertUserPoint(id, amount);
        //then
        Assertions.assertThat(userPoint).isEqualTo(new UserPoint(id, amount, userPoint.updateMillis()));
    }

    @Test
    @DisplayName("두번째 유저별 포인트 저장하는 서비스로직 : 충전한 유저 id와 충전양을 리턴")
    void insert_point_service2() {
        //given
        long id = 1L;
        long amount = 10L;
        long millis = System.currentTimeMillis();
        //when
        UserPoint userPoint = pointService.insertUserPoint(id, amount);
        //then
        Assertions.assertThat(userPoint).isEqualTo(new UserPoint(id, amount, userPoint.updateMillis()));
    }

    @Test
    @DisplayName("세번째 유저별 포인트 저장하는 서비스로직: dao, repository 연동하기")
    void insert_point_service_connect_repository() {
        //given
        long id = 1L;
        long amount = 10L;
        //when
        UserPoint userPoint = pointService.insertUserPoint(id, amount);
        //then
        Assertions.assertThat(userPoint).isEqualTo(new UserPoint(id, amount, userPoint.updateMillis()));
    }

    @Test
    @DisplayName("네번째 유저별 포인트 저장하는 서비스로직: 저장된 유저별 포인트와 조회하는 로직을 구현하여 맞는지 비교")
    void insert_point_service_create_randomUUID() {
        //given
        long id = Math.abs(UUID.randomUUID().getLeastSignificantBits());
        long amount = 10L;
        //when
        UserPoint userPoint = pointService.insertUserPoint(id, amount);
        //then
        UserPoint selectPoint = pointService.selectUserPoint(id);
        Assertions.assertThat(userPoint).isEqualTo(selectPoint);
    }



}
