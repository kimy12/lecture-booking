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
    public ApiResponse<List<LectureResponseDto.AvailableLectures>> getAllAvailableLectures(
            @ModelAttribute LectureRequestDto.LectureRequest request) {

        List<Lectures> lectures = lectureFacade.showAllAvailableLectures(request.lectureAt());

        if (lectures == null || lectures.isEmpty()) {
            return ApiResponse.of(HttpStatus.NO_CONTENT, null);
        }

        return ApiResponse.ok(lectures.stream()
                .map(l -> LectureResponseDto.AvailableLectures.builder()
                .id(l.getId())
                .title(l.getTitle())
                .status(l.getStatus())
                .lecturer(l.getLecturer())
                .lectureAt(l.getLectureAt())
                .startAt(l.getStartAt())
                .endAt(l.getEndAt())
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
