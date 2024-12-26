package com.lecture.lectureBooking.application.facade;

import com.lecture.lectureBooking.application.service.LectureService;
import com.lecture.lectureBooking.domain.LectureMembers;
import com.lecture.lectureBooking.domain.Lectures;
import com.lecture.lectureBooking.presentation.dto.LectureRequestDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.util.List;

@Component
@RequiredArgsConstructor
public class LectureFacade {
    private final LectureService lectureService;

    public List<Lectures> showAllAvailableLectures(LocalDate lectureAt) {
        return lectureService.getAllAvailableLectures(lectureAt);
    }

    public List<LectureMembers> showBookedLectures(long userId) {
        return lectureService.getAllBookedLecturesByUser(userId);
    }

    public LectureMembers bookLecture(LectureRequestDto.BookLectureForm request) {
        return lectureService.bookLecture(request);
    }
}
