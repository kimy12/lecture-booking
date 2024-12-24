package com.lecture.lectureBooking.presentation.controller;

import com.lecture.lectureBooking.application.facade.LectureFacade;
import com.lecture.lectureBooking.application.service.LectureService;
import com.lecture.lectureBooking.common.ApiResponse;
import com.lecture.lectureBooking.domain.Lectures;
import com.lecture.lectureBooking.presentation.dto.LectureResponseDto;
import lombok.RequiredArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDate;
import java.util.List;

@RequiredArgsConstructor
@RestController
@RequestMapping("/lecture")
public class LectureController {

    private final LectureFacade lectureFacade;

    @GetMapping("/api/v1/Lectures")
    public ApiResponse<List<LectureResponseDto.LectureResponse>> getAllAvailableLectures(@RequestParam("lectureAt")
                                                                                         @DateTimeFormat(pattern = "yyyyMMdd") LocalDate lectureAt) {
        List<Lectures> lectures = lectureFacade.showAllAvailableLectures(lectureAt);

        if (lectures == null || lectures.isEmpty()) {
            return ApiResponse.of(HttpStatus.NO_CONTENT, null);
        }

        return ApiResponse.ok(lectures.stream()
                .map(c -> LectureResponseDto.LectureResponse.builder()
                .id(c.getId())
                .title(c.getTitle())
                .status(c.getStatus())
                .lecturer(c.getLecturer())
                .lectureAt(c.getLectureAt())
                .startAt(c.getStartAt())
                .endAt(c.getEndAt())
                .build())
                .toList());
    }

}