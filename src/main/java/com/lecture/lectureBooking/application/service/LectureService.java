package com.lecture.lectureBooking.application.service;

import com.lecture.lectureBooking.domain.LectureBookingStatus;
import com.lecture.lectureBooking.domain.LectureMembers;
import com.lecture.lectureBooking.domain.Lectures;
import com.lecture.lectureBooking.domain.repository.LectureMemberRepository;
import com.lecture.lectureBooking.domain.repository.LectureRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Transactional(readOnly = true)
@RequiredArgsConstructor
@Service
public class LectureService {

    private final LectureRepository lectureRepository;

    private final LectureMemberRepository lectureMemberRepository;

    public List<Lectures> getAllAvailableLectures(LocalDate lectureAt) {
        return lectureRepository.findLecturesAllBy(lectureAt, LectureBookingStatus.AVAILABLE);
    }

    public List<LectureMembers> getAllBookedLecturesByUser(long userId) {
        return lectureMemberRepository.findByLectureId(userId);
    }
}
