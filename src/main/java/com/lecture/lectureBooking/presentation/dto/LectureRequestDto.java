package com.lecture.lectureBooking.presentation.dto;


import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.Builder;
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

    public record BookLectureForm(

            @Positive(message = "잘못된 유저 아이디 입니다.")
            long userId,

            @Positive(message = "잘못된 강의 정보 입니다.")
            long lectureId
    ) { }
}
