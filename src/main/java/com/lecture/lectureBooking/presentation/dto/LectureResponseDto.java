package com.lecture.lectureBooking.presentation.dto;

import com.lecture.lectureBooking.domain.LectureBookingStatus;
import lombok.Builder;

import java.time.LocalDateTime;

public class LectureResponseDto {
    @Builder
    public record LectureResponse(
            long id,
            String title,
            String lecturer,
            LectureBookingStatus status,
            LocalDateTime lectureAt,
            LocalDateTime startAt,
            LocalDateTime endAt
    ) {
    }
}
