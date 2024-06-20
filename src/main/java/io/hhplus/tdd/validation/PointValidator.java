package io.hhplus.tdd.validation;

import io.hhplus.tdd.ErrorResponse;
import io.hhplus.tdd.exception.BadRequestPointException;
import io.hhplus.tdd.exception.NotEnoughPointException;

public class PointValidator {

    public static PointValidationType pointOfLackValidate(long point) {
        if(point <= 0 )  return PointValidationType.LACK;
        return PointValidationType.VALID;
    }

    public static PointValidationType requestPointValidate(long point) {
        if(point <= 0 )  return PointValidationType.INVALID;
        return PointValidationType.VALID;
    }


    public static PointValidationType requestPointValidate(long point, long id) {
        if(point <= 0 || id <= 0)  return PointValidationType.INVALID;
        return PointValidationType.VALID;
    }

    public static void validate(PointValidationType pointValidationType) throws Exception {
        if (pointValidationType.equals(PointValidationType.LACK)) throw new NotEnoughPointException(new ErrorResponse("500", "포인트가 부족합니다."));
        else if(pointValidationType.equals(PointValidationType.INVALID)) throw new BadRequestPointException(new ErrorResponse("500", "잘못된 요청입니다."));
    }
}
