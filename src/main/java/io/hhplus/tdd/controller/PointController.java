package io.hhplus.tdd.controller;

import io.hhplus.tdd.concurrency.ConCurrencyStatus;
import io.hhplus.tdd.concurrency.threadlocal.ConCurrencyControl;
import io.hhplus.tdd.point.PointHistory;
import io.hhplus.tdd.point.UserPoint;
import io.hhplus.tdd.service.PointService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/point")
public class PointController {

    private static final Logger log = LoggerFactory.getLogger(PointController.class);

    private PointService pointService;
    private ConCurrencyControl control;

    @Autowired
    public PointController(PointService pointService, ConCurrencyControl control) {
        this.pointService = pointService;
        this.control = control;
    }


    /**
     * 특정 유저의 포인트를 조회 Controller
     * @PathVariable id
     * @return ResponseEntity<UserPoint>
     */
    @GetMapping("{id}")
    public ResponseEntity<UserPoint> point(
            @PathVariable(name = "id") long id
    ) throws Exception  {
        return ResponseEntity.ok().body(pointService.selectUserPoint(id));
    }

    /**
     * 특정 유저의 포인트 충전/이용 내역을 조회 Controller
     * @PathVariable id
     * @return ResponseEntity<List<PointHistory>>
     */
    @GetMapping("{id}/histories")
    public ResponseEntity<List<PointHistory>> history(
            @PathVariable(name = "id") long id
    )  throws Exception {
        return ResponseEntity.ok().body(pointService.selectPointHistory(id));
    }

    /**
     * 특정 유저의 포인트를 충전 Controller
     * @PathVariable id
     * @RequestBody amount
     * @return ResponseEntity<UserPoint>
     */
    @PatchMapping("{id}/charge")
    public ResponseEntity<UserPoint> charge(
            @PathVariable(name = "id") long id,
            @RequestBody long amount
    ) throws Exception  {
        return ResponseEntity.ok().body(pointService.insertUserPoint(id, amount));
    }

    /**
     * 특정 유저의 포인트를 사용 Controller
     * @PathVariable id
     * @RequestBody amount
     * @return ResponseEntity<UserPoint>
     */
    @PatchMapping("{id}/use")
    public ResponseEntity<UserPoint> use (
            @PathVariable(name = "id") long id,
            @RequestBody long amount
    ) throws Exception {
        ConCurrencyStatus status = control.begin();
        ResponseEntity<UserPoint> responseEntity = ResponseEntity.ok().body(pointService.useUserPoint(id, amount));
        control.end(status);
        return responseEntity;
    }
}
