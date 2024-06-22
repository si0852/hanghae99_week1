package io.hhplus.tdd.controller;

import io.hhplus.tdd.dto.PointHistoryDto;
import io.hhplus.tdd.dto.UserDto;
import io.hhplus.tdd.point.PointHistory;
import io.hhplus.tdd.point.UserPoint;
import io.hhplus.tdd.service.PointService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/point")
public class PointController {

    private static final Logger log = LoggerFactory.getLogger(PointController.class);

    private PointService pointService;


    public PointController(PointService pointService) {
        this.pointService = pointService;
    }


    /**
     * 특정 유저의 포인트를 조회 Controller
     * @PathVariable id
     * @return ResponseEntity<UserDto>
     */
    @GetMapping("{id}")
    public ResponseEntity<UserDto> point(
            @PathVariable(name = "id") long id
    ) throws Exception  {
        UserPoint selectUserPoint = pointService.selectUserPoint(id);
        return ResponseEntity.ok().body(new UserDto(selectUserPoint.id(), selectUserPoint.point(), selectUserPoint.updateMillis()));
    }

    /**
     * 특정 유저의 포인트 충전/이용 내역을 조회 Controller
     * @PathVariable id
     * @return ResponseEntity<List<PointHistoryDto>>
     */
    @GetMapping("{id}/histories")
    public ResponseEntity<List<PointHistoryDto>> history(
            @PathVariable(name = "id") long id
    )  throws Exception {
        List<PointHistory> pointHistories = pointService.selectPointHistory(id);
        return ResponseEntity.ok().body(pointHistories.stream()
                .map(data -> new PointHistoryDto(data.id(), data.userId(), data.amount(), data.type(), data.updateMillis()))
                .collect(Collectors.toList()));
    }

    /**
     * 특정 유저의 포인트를 충전 Controller
     * @PathVariable id
     * @RequestBody amount
     * @return ResponseEntity<UserDto>
     */
    @PatchMapping("{id}/charge")
    public ResponseEntity<UserDto> charge(
            @PathVariable(name = "id") long id,
            @RequestBody long amount
    ) throws Exception  {
        UserPoint userPoint = pointService.insertUserPoint(id, amount);
        return ResponseEntity.ok().body(new UserDto(userPoint.id(), userPoint.point(), userPoint.updateMillis()));
    }

    /**
     * 특정 유저의 포인트를 사용 Controller
     * @PathVariable id
     * @RequestBody amount
     * @return ResponseEntity<UserDto>
     */
    @PatchMapping("{id}/use")
    public ResponseEntity<UserDto> use (
            @PathVariable(name = "id") long id,
            @RequestBody long amount
    ) throws Exception {
        UserPoint userPoint = pointService.useUserPoint(id, amount);
        return ResponseEntity.ok().body(new UserDto(userPoint.id(), userPoint.point(), userPoint.updateMillis()));
    }
}
