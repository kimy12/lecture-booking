package com.lecture.lectureBooking.presentation.dto;


import java.time.LocalDateTime;

public class LectureRequestDto {
    public record LectureRequest(
            LocalDateTime lectureAt
    ) { }
}
