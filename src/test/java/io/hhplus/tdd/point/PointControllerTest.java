package io.hhplus.tdd.point;

import com.fasterxml.jackson.databind.ObjectMapper;
import io.hhplus.tdd.service.PointServiceTest;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;

import java.util.HashMap;
import java.util.Map;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.patch;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.springframework.web.servlet.function.RequestPredicates.contentType;

@AutoConfigureMockMvc
@SpringBootTest
class PointControllerTest {

    private static final Logger log = LoggerFactory.getLogger(PointControllerTest.class);
    @Autowired
    MockMvc mvc;

    @Autowired
    ObjectMapper objectMapper;

    @Test
    @DisplayName("특정 유저의 포인트를 조회하는 기능 Controller 첫번째 테스트: id를 넣었을때 성공")
    void point() throws Exception{
        //given
        long id = 1L;
        UserPoint userPoint = new UserPoint(0, 0, 0);
        String content = objectMapper.writeValueAsString(userPoint);
        //when
        //then
        mvc.perform(get("/point/"+id))
                .andExpect(status().isOk())
                .andExpect(content().json(content));
    }

    @Test
    @DisplayName("특정 유저의 포인트를 충전하는 기능 Controller 첫번째 테스트: id, amount 넣었을때 성공")
    void charge() throws Exception{
        //given
        long id = 1L;
        long amount = 1000L;
        UserPoint userPoint = new UserPoint(0, 0, 0);
        String content = objectMapper.writeValueAsString(userPoint);

        //when
        //then
        mvc.perform(patch( "/point/"+id+"/charge")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(String.valueOf(amount)))
                .andExpect(status().isOk())
                .andExpect(content().json(content));
    }

    @Test
    @DisplayName("특정 유저의 포인트를 사용하는 기능 Controller Test: id 넣을을때 200 코드 리턴")
    void use() throws Exception{
            //given
            long userId = 1L;
            long point = 1000L;
            UserPoint userPoint = new UserPoint(0, 0, 0);
            String content = objectMapper.writeValueAsString(userPoint);

            //when
            //then
            mvc.perform(patch("/point/" + userId + "/use")
                            .contentType(MediaType.APPLICATION_JSON)
                            .content(String.valueOf(point)))
                    .andExpect(status().isOk())
                    .andExpect(content().json(content));

    }

//    @Test
//    void history() {
//    }
//

//    @Test
//    void use() {
//    }
}