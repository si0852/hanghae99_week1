package io.hhplus.tdd.point;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@AutoConfigureMockMvc
@SpringBootTest
class PointControllerTest {

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

//    @Test
//    void history() {
//    }
//
//    @Test
//    void charge() {
//    }
//
//    @Test
//    void use() {
//    }
}