package com.lecture.lectureBooking.presentation.dto;


import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Pattern;

import java.time.LocalDateTime;

public class LectureRequestDto {
    public record LectureRequest(
            @Pattern(regexp = "\\d{4}\\d{2}\\d{2}", message = "날짜양식을 확인해주세요.")
            LocalDateTime lectureAt
    ) { }
    public record BookedLectureByUser(
            @NotEmpty(message = "유저아이디는 필수입니다.")
            long userId
    ) { }
}
