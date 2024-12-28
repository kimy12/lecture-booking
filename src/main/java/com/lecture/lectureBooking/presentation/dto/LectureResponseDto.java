package com.lecture.lectureBooking.presentation.dto;

import lombok.Builder;

import java.time.LocalDateTime;

public class LectureResponseDto {
    @Builder
    public record AvailableLectures(
            long id,
            String title,
            String lecturer,
            LocalDateTime lectureAt,
            LocalDateTime startAt,
            LocalDateTime endAt
    ) {
    }

    @Builder
    public record BookedLectures(
            long id,
            String title,
            String lecturer,
            LocalDateTime lectureAt,
            LocalDateTime createdAt
    ) {
    }

    @Builder
    public record SuccessedBookLectures(
            long userId,
            long lectureId,
            LocalDateTime createAt
    ) {
    }
}
