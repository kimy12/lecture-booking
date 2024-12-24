package com.lecture.lectureBooking.application.service;

import com.lecture.lectureBooking.domain.LectureBookingStatus;
import com.lecture.lectureBooking.domain.Lectures;
import com.lecture.lectureBooking.domain.repository.LectureRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;

@Transactional(readOnly = true)
@RequiredArgsConstructor
@Service
public class LectureService {

    private final LectureRepository lectureRepository;

    public List<Lectures> getAllAvailableLectures(LocalDate lectureAt) {
        return lectureRepository.findLecturesAllBy(lectureAt, LectureBookingStatus.AVAILABLE);
    }

}