package com.lecture.lectureBooking.application.service;

import com.lecture.lectureBooking.domain.LectureMembers;
import com.lecture.lectureBooking.domain.Lectures;
import com.lecture.lectureBooking.domain.repository.LectureMemberRepository;
import com.lecture.lectureBooking.domain.repository.LectureRepository;
import com.lecture.lectureBooking.presentation.dto.LectureRequestDto;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.tuple;


@SpringBootTest
class LectureServiceTest {
    @Autowired
    private LectureService lectureService;

    @Autowired
    private LectureRepository lectureRepository;

    @Autowired
    private LectureMemberRepository lectureMemberRepository;


    @BeforeEach
     void tearDown() {
        lectureMemberRepository.deleteAllInBatch();
        lectureRepository.deleteAllInBatch();
    }

    @DisplayName("날짜별로 수강 가능한 강의 목록을 가져온다.")
    @Test
    void getAllLecturesByDate () {
        // given
        LocalDateTime lectureAt = LocalDateTime.of(2024, 12, 24, 14, 0);
        Lectures availableLecture1 = createLecture("특강1","강사1",lectureAt);
        Lectures availableLecture2 = createLecture("특강2","강사2",lectureAt);
        lectureRepository.saveAll(List.of(availableLecture1,availableLecture2));

        // when
        List<Lectures> allAvailableLectures = lectureService.getAllAvailableLectures(LocalDate.of(2024, 12, 24));

        // then
        assertThat(allAvailableLectures).hasSize(2)
                .extracting("title","lecturer")
                .containsExactlyInAnyOrder(
                        tuple("특강1", "강사1"),
                        tuple("특강2", "강사2")
                );
    }

    @DisplayName("해당 아이디로 예약완료 된 목록을 가져온다.")
    @Test
    void getBookedLecturesById () {
        // given
        long userId = 5;
        LocalDateTime lectureStartAt = LocalDateTime.of(2024, 12, 24, 14, 0);
        Lectures availableLecture1 = createLecture("특강1","강사1",lectureStartAt);
        Lectures savedLecture = lectureRepository.save(availableLecture1);

        LectureMembers lectureMember1 = createLectureMember(lectureStartAt, userId, savedLecture);

        lectureMemberRepository.save(lectureMember1);

        // when
        List<LectureMembers> allAvailableLectures = lectureService.getAllBookedLecturesByUser(userId);

        // then
        assertThat(allAvailableLectures).hasSize(1)
                .extracting("userId","lecture.title","lecture.lecturer")
                .containsExactlyInAnyOrder(
                        tuple(5L,"특강1", "강사1")
                );
    }

    @DisplayName("강의를 신청한다.")
    @Test
    void bookingLecture() {
        // given
        LocalDateTime lectureStartAt = LocalDateTime.of(2024, 12, 29, 14, 0);
        Lectures availableLecture1 = createLecture("특강1","강사1",lectureStartAt);
        Lectures savedLecture = lectureRepository.save(availableLecture1);

        LectureRequestDto.BookLectureForm bookLectureForm = new LectureRequestDto.BookLectureForm(1, savedLecture.getId());

        // when
        LectureMembers bookedLectureInfo = lectureService.bookLecture(bookLectureForm);

        // then
        assertThat(bookedLectureInfo).isNotNull();
    }

    @DisplayName("신청완료한 강의를 또 신청한 경우 예외가 발생한다.")
    @Test
    void bookingLectureWithSameInfo() {
        // given
        LocalDateTime lectureStartAt = LocalDateTime.of(2024, 12, 29, 14, 0);
        Lectures availableLecture1 = createLecture("특강1","강사1",lectureStartAt);
        Lectures savedLecture = lectureRepository.save(availableLecture1);

        LectureRequestDto.BookLectureForm bookLectureForm = new LectureRequestDto.BookLectureForm(1,savedLecture.getId());
        lectureService.bookLecture(bookLectureForm);

        // when // then
        IllegalArgumentException e = Assertions.assertThrows(IllegalArgumentException.class, () -> lectureService.bookLecture(bookLectureForm));
        assertThat(e.getMessage()).isEqualTo("이미 신청완료한 강의 입니다.");

    }

    @DisplayName("강의 정보가 없는 경우 신청시 예외가 발생한다.")
    @Test
    void bookingLectureWithNoLectureInfo() {
        // given
        LectureRequestDto.BookLectureForm bookLectureForm = new LectureRequestDto.BookLectureForm(100,50);

        // when
        IllegalArgumentException e = Assertions.assertThrows(IllegalArgumentException.class, () -> lectureService.bookLecture(bookLectureForm));

        // then
        assertThat(e.getMessage()).isEqualTo("존재하지 않는 강의 입니다.");

    }

    private static LectureMembers createLectureMember(LocalDateTime lectureStartAt, long userId, Lectures lectures) {
        return LectureMembers.builder()
                .userId(userId)
                .lecture(lectures)
                .createdAt(lectureStartAt)
                .build();
    }

    private static Lectures createLecture(String title, String lecturer, LocalDateTime lectureAt) {
        return Lectures.builder()
                .title(title)
                .lecturer(lecturer)
                .lectureAt(lectureAt)
                .startAt(LocalDateTime.now().minusDays(1))
                .endAt(LocalDateTime.now().plusDays(1))
                .build();
    }
}