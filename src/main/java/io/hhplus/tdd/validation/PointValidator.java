package io.hhplus.tdd.validation;

public class PointValidator {

    public static PointValidationType pointOfLackValidate(long point) {
        if(point < 0 )  return PointValidationType.LACK;
        return PointValidationType.VALID;
    }
}
