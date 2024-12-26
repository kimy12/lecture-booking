package com.lecture.lectureBooking.domain;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.*;

class LecturesTest {

    @DisplayName("신청인원이 초과되면 예외가 발생한다.")
    @Test
    void addLectureMemberNumber() {
        // given
        Lectures lecture = Lectures.builder()
                .title("강의")
                .lecturer("강사")
                .lectureAt(LocalDateTime.of(2024, 12, 25, 14, 0))
                .startAt(LocalDateTime.of(2024, 12, 20, 14, 0))
                .endAt(LocalDateTime.of(2024, 12, 24, 14, 0))
                .memberCount(29)
                .build();

        // when
        lecture.addMemberCount();

        // then
        assertEquals(30, lecture.getMemberCount());

    }

    @DisplayName("신청인원이 초과되면 예외가 발생한다.")
    @Test
    void overLectureMemberNumber() {
        // given
        Lectures lecture = Lectures.builder()
                .title("강의")
                .lecturer("강사")
                .lectureAt(LocalDateTime.of(2024, 12, 25, 14, 0))
                .startAt(LocalDateTime.of(2024, 12, 20, 14, 0))
                .endAt(LocalDateTime.of(2024, 12, 24, 14, 0))
                .memberCount(30)
                .build();

        // when // then
        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, lecture::addMemberCount);
        assertEquals("신청인원이 초과된 강의입니다.", exception.getMessage());
    }

    @DisplayName("이미 종료한 강의의 경우, 예외가 발생한다.")
    @Test
    void finishedLectureAlready() {
        // given
        Lectures lecture = Lectures.builder()
                .title("강의")
                .lecturer("강사")
                .lectureAt(LocalDateTime.of(2024, 12, 25, 14, 0))
                .startAt(LocalDateTime.of(2024, 12, 25, 14, 0))
                .endAt(LocalDateTime.of(2024, 12, 26, 14, 0))
                .memberCount(29)
                .build();

        // when // then
        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, lecture::checkLectureSchedules);
        assertEquals("이미 종료한 강의입니다.", exception.getMessage());
    }

    @DisplayName("강의의 신청기간이 아닌 경우, 예외가 발생한다.")
    @Test
    void notBookingPeriod() {
        // given
        Lectures lecture = Lectures.builder()
                .title("강의")
                .lecturer("강사")
                .lectureAt(LocalDateTime.of(2025, 12, 30, 14, 0))
                .startAt(LocalDateTime.of(2025, 12, 27, 14, 0))
                .endAt(LocalDateTime.of(2025, 12, 28, 14, 0))
                .memberCount(29)
                .build();

        // when // then
        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, lecture::checkLectureSchedules);
        assertEquals("강의 신청기간이 아닙니다.", exception.getMessage());
    }


}