package io.hhplus.tdd.validation;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
public class PointValidationCheckTest {

    @Test
    @DisplayName("사용한 포인트가 0보다 작을떄, return LACK")
    void lackOfPoint() {
        //given
        long point = -1;
        //when
        PointValidationType pointValidationType = PointValidator.pointOfLackValidate(point);
        //then
        assertEquals(PointValidationType.LACK,pointValidationType);
    }

    @Test
    @DisplayName("사용한 포인트가 0보다 클떄, return VALID")
    void validOfPoint() {
        //given
        long point = 1;
        //when
        PointValidationType pointValidationType = PointValidator.pointOfLackValidate(point);
        //then
        assertEquals(PointValidationType.VALID,pointValidationType);
    }

    @Test
    @DisplayName("요청준 포인트가 0보다 작을때, return INVALID")
    void requestValidOfPoint() {
        //given
        long point = -1;
        //when
        PointValidationType pointValidationType = PointValidator.requestPointValidate(point);
        //then
        assertEquals(PointValidationType.INVALID,pointValidationType);
    }

    @Test
    @DisplayName("요청준 포인트가 0보다 클때, return VALID")
    void requestValidOfPoint2() {
        //given
        long point = 1;
        //when
        PointValidationType pointValidationType = PointValidator.requestPointValidate(point);
        //then
        assertEquals(PointValidationType.VALID,pointValidationType);
    }
}
