package com.lecture.lectureBooking.presentation.controller;

import com.lecture.lectureBooking.application.facade.LectureFacade;
import com.lecture.lectureBooking.application.service.LectureService;
import com.lecture.lectureBooking.common.ApiResponse;
import com.lecture.lectureBooking.domain.LectureMembers;
import com.lecture.lectureBooking.domain.Lectures;
import com.lecture.lectureBooking.presentation.dto.LectureRequestDto;
import com.lecture.lectureBooking.presentation.dto.LectureResponseDto;
import lombok.RequiredArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;

@RequiredArgsConstructor
@RestController
@RequestMapping("/lecture")
public class LectureController {

    private final LectureFacade lectureFacade;

    @GetMapping("/api/v1/lectures")
    public ApiResponse<List<LectureResponseDto.AvailableLectures>> getAllAvailableLectures(@RequestParam("lectureAt")
                                                                                           @DateTimeFormat(pattern = "yyyyMMdd") LocalDate lectureAt) {
        List<Lectures> lectures = lectureFacade.showAllAvailableLectures(lectureAt);

        if (lectures == null || lectures.isEmpty()) {
            return ApiResponse.of(HttpStatus.NO_CONTENT, null);
        }

        return ApiResponse.ok(lectures.stream()
                .map(c -> LectureResponseDto.AvailableLectures.builder()
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

    @GetMapping("/api/v1/bookedLectures")
    public ApiResponse<List<LectureResponseDto.BookedLectures>> getBookedLectures
            (@ModelAttribute LectureRequestDto.BookedLectureByUser request) {
        List<LectureMembers> lectureMembers = lectureFacade.showBookedLectures(request.userId());

        if (lectureMembers == null || lectureMembers.isEmpty()) {
            return ApiResponse.of(HttpStatus.NO_CONTENT, null);
        }
        return ApiResponse.ok(lectureMembers.stream()
                .map(l -> LectureResponseDto.BookedLectures.builder()
                .id(l.getId())
                .title(l.getLecture().getTitle())
                .lecturer(l.getLecture().getLecturer())
                .lectureAt(l.getLecture().getLectureAt())
                .createdAt(l.getCreatedAt())
                .build())
                .toList());
    }

}
