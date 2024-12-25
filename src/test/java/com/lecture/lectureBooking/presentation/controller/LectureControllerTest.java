package com.lecture.lectureBooking.presentation.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.lecture.lectureBooking.application.facade.LectureFacade;
import com.lecture.lectureBooking.application.service.LectureService;
import com.lecture.lectureBooking.domain.LectureBookingStatus;
import com.lecture.lectureBooking.domain.LectureMembers;
import com.lecture.lectureBooking.domain.Lectures;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

import static com.lecture.lectureBooking.domain.LectureBookingStatus.AVAILABLE;
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
    protected LectureFacade lectureFacade;

    @DisplayName("날짜별로 현재 신청 가능한 특강 목록을 조회한다.")
    @Test
    void getAllLecturesByDate() throws Exception {
        // given
        String lectureAt = "20241224";
        LocalDateTime lectureStartAt = LocalDateTime.of(2024, 12, 24, 14, 0);
        List<Lectures> lectures = List.of(
                createLecture("특강1","강사1",lectureStartAt),
                createLecture("특강3","강사3",lectureStartAt)
        );

        Mockito.when(lectureFacade.showAllAvailableLectures(LocalDate.of(2024,12,24)))
                .thenReturn(lectures);

        // when // then
        mockMvc.perform(
                get("/lecture/api/v1/lectures")
                        .param("lectureAt", lectureAt)
                )
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.status").value("OK"))
                .andExpect(jsonPath("$.data").isArray())
                ;
    }

    @DisplayName("날짜별로 현재 신청 가능한 특강 목록 조회 시, 목록이 존재하지 않은 경우 204 No Content를 반환한다.")
    @Test
    void nothingToGetAvailableLecturesByDate() throws Exception {
        // given
        String lectureAt = "20241224";

        Mockito.when(lectureFacade.showAllAvailableLectures(LocalDate.of(2024,12,24)))
                .thenReturn(null);

        // when // then
        mockMvc.perform(
                        get("/lecture/api/v1/lectures")
                                .param("lectureAt", lectureAt)
                )
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.status").value("NO_CONTENT"))
                .andExpect(jsonPath("$.message").value("NO_CONTENT"))
                .andExpect(jsonPath("$.code").value("204"))
                .andExpect(jsonPath("$.data").isEmpty())
        ;
    }

    @DisplayName("특정 유저 아이디로 신청 완료된 특강 목록을 조회한다.")
    @Test
    void getBookedLecturesByUser() throws Exception {
        // given
        String lectureAt = "20241224";
        LocalDateTime lectureStartAt = LocalDateTime.of(2024, 12, 24, 14, 0);

        LectureMembers bookedLecture1 = getBookedLecture(1, lectureStartAt, createLecture("특강1", "강사1", lectureStartAt));
        LectureMembers bookedLecture2 = getBookedLecture(1, lectureStartAt, createLecture("특강2", "강사2", lectureStartAt));

        List<LectureMembers> lectureMembers = List.of(bookedLecture1, bookedLecture2);

        Mockito.when(lectureFacade.showBookedLectures(1))
                .thenReturn(lectureMembers);

        // when // then
        mockMvc.perform(
                        get("/lecture/api/v1/bookedLectures")
                                .param("userId", "1")
                )
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.status").value("OK"))
                .andExpect(jsonPath("$.data").isArray())
        ;
    }

    @DisplayName("특정 유저 아이디로 신청 완료된 특강 목록을 조회 시, 목록이 존재하지 않는경우 204 No Content를 반환한다.")
    @Test
    void nothingToGetBookedLecturesByUser() throws Exception {
        // given
        long userId = 1;
        Mockito.when(lectureFacade.showBookedLectures(userId))
                .thenReturn(null);

        // when // then
        mockMvc.perform(
                        get("/lecture/api/v1/bookedLectures")
                                .param("userId", "1")
                )
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.status").value("NO_CONTENT"))
                .andExpect(jsonPath("$.message").value("NO_CONTENT"))
                .andExpect(jsonPath("$.code").value("204"))
                .andExpect(jsonPath("$.data").isEmpty())
        ;
    }

    private static LectureMembers getBookedLecture(long id,LocalDateTime lectureStartAt, Lectures lecture) {
        return LectureMembers.builder()
                .userId(id)
                .lecture(lecture)
                .createdAt(lectureStartAt)
                .build();
    }

    private static Lectures createLecture(String title, String lecturer, LocalDateTime lectureAt) {
        return Lectures.builder()
                .title(title)
                .lecturer(lecturer)
                .lectureAt(lectureAt)
                .status(AVAILABLE)
                .build();
    }

}