package com.lecture.lectureBooking.presentation.dto;


import jakarta.validation.constraints.*;

import java.time.LocalDateTime;

public class LectureRequestDto {
    public record LectureRequest(
            LocalDateTime lectureAt
    ) { }
    public record BookedLectureByUser(
            long userId
    ) { }
}
