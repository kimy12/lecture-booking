package com.lecture.lectureBooking.presentation.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.lecture.lectureBooking.application.service.LectureService;
import com.lecture.lectureBooking.domain.Lectures;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

import java.time.LocalDate;
import java.util.List;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(LectureController.class)
class LectureControllerTest {
    @Autowired
    protected MockMvc mockMvc;

    @Autowired
    protected ObjectMapper objectMapper;

    @MockitoBean
    protected LectureService lectureService;

    @DisplayName("날짜별로 현재 신청 가능한 특강 목록을 조회한다.")
    @Test
    void getAllLecturesByDate() throws Exception {
        // given
        String lectureAt = "20241224";
        List<Lectures> lectures = List.of();

        Mockito.when(lectureService.getAllAvailableLectures(LocalDate.of(2024,12,24)))
                .thenReturn(lectures);

        // when // then
        mockMvc.perform(
                get("/lecture/api/v1/Lectures")
                        .param("lectureAt", lectureAt)
                )
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.status").value("OK"))
                .andExpect(jsonPath("$.data").isArray())
                ;
    }

}