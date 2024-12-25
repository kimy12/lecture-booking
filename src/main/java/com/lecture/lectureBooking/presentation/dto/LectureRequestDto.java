package com.lecture.lectureBooking.presentation.dto;


import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDate;

public class LectureRequestDto {
    public record LectureRequest(
            @DateTimeFormat(pattern = "yyyyMMdd")
            LocalDate lectureAt
    ) { }
    public record BookedLectureByUser(
            long userId
    ) { }
}
