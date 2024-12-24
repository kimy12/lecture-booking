package com.lecture.lectureBooking.application.service;

import com.lecture.lectureBooking.domain.LectureBookingStatus;
import com.lecture.lectureBooking.domain.Lectures;
import com.lecture.lectureBooking.domain.repository.LectureRepository;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

import static com.lecture.lectureBooking.domain.LectureBookingStatus.AVAILABLE;
import static com.lecture.lectureBooking.domain.LectureBookingStatus.UPCOMING;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.tuple;

@ActiveProfiles("test")
@SpringBootTest
class LectureServiceTest {
    @Autowired
    private LectureService lectureService;

    @Autowired
    private LectureRepository lectureRepository;

    @AfterEach
    void tearDown() {
        lectureRepository.deleteAllInBatch();
    }

    @DisplayName("날짜별로 수강 가능한 강의 목록을 가져온다.")
    @Test
    void getAllLecturesByDate () {
        // given
        LocalDateTime lectureAt = LocalDateTime.of(2024, 12, 24, 14, 0);
        Lectures availableLecture1 = createLecture("특강1","강사1",lectureAt, AVAILABLE);
        Lectures availableLecture2 = createLecture("특강2","강사2",lectureAt, UPCOMING);
        Lectures availableLecture3 = createLecture("특강3","강사3",lectureAt, AVAILABLE);
        lectureRepository.saveAll(List.of(availableLecture1,availableLecture2,availableLecture3));

        // when
        List<Lectures> allAvailableLectures = lectureService.getAllAvailableLectures(LocalDate.of(2024, 12, 24));

        // then
        assertThat(allAvailableLectures).hasSize(2)
                .extracting("id","title","lecturer", "status")
                .containsExactlyInAnyOrder(
                        tuple(1L,"특강1", "강사1", AVAILABLE),
                        tuple(3L,"특강3", "강사3", AVAILABLE)
                );
    }

    private static Lectures createLecture(String title, String lecturer, LocalDateTime lectureAt, LectureBookingStatus status) {
        return Lectures.builder()
                .title(title)
                .lecturer(lecturer)
                .lectureAt(lectureAt)
                .status(status)
                .build();
    }
}