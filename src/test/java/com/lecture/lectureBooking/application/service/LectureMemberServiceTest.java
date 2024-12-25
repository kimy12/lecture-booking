package com.lecture.lectureBooking.application.service;

import com.lecture.lectureBooking.domain.LectureMembers;
import com.lecture.lectureBooking.domain.Lectures;
import com.lecture.lectureBooking.domain.repository.LectureMemberRepository;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

import java.time.LocalDateTime;
import java.util.List;

import static com.lecture.lectureBooking.domain.LectureBookingStatus.AVAILABLE;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.tuple;

@ActiveProfiles("test")
@SpringBootTest
class LectureMemberServiceTest {

    @Autowired
    private LectureMemberRepository lectureMemberRepository;

    @Autowired
    private LectureMemberService lectureMemberService;

    @AfterEach
    void tearDown() {
        lectureMemberRepository.deleteAllInBatch();
    }

    @DisplayName("해당 아이디로 예약완료 된 목록을 가져온다.")
    @Test
    void getBookedLecturesById () {
        // given
        long userId = 1;
        LocalDateTime lectureStartAt = LocalDateTime.of(2024, 12, 24, 14, 0);

        Lectures lecture = createLecture("특강1", "강사1", LocalDateTime.of(2024, 12, 25, 14, 0));
        LectureMembers lectureMember1 = createLectureMember(lectureStartAt, userId);
        lectureMember1.setLecture(lecture);
        LectureMembers lectureMember2 = createLectureMember(lectureStartAt, 2);
        lectureMember2.setLecture(lecture);
        LectureMembers lectureMember3 = createLectureMember(lectureStartAt, 3);
        lectureMember3.setLecture(lecture);

        lectureMemberRepository.save(lectureMember1);

        // when
        List<LectureMembers> allAvailableLectures = lectureMemberService.getAllBookedLecturesByUser(userId);

        // then
        assertThat(allAvailableLectures).hasSize(1)
                .extracting("userId","lecture.title","lecture.lecturer", "lecture.status")
                .containsExactlyInAnyOrder(
                        tuple(1L,"특강1", "강사1", AVAILABLE)
                );
    }

    private static LectureMembers createLectureMember(LocalDateTime lectureStartAt, long userId) {
        return LectureMembers.builder()
                .userId(userId)
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