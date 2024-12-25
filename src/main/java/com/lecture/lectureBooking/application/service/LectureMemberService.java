package com.lecture.lectureBooking.application.service;

import com.lecture.lectureBooking.domain.LectureMembers;
import com.lecture.lectureBooking.domain.repository.LectureMemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Transactional(readOnly = true)
@RequiredArgsConstructor
@Service
public class LectureMemberService {

    private final LectureMemberRepository lectureMemberRepository;

    public List<LectureMembers> getAllBookedLecturesByUser(long userId) {
        return lectureMemberRepository.findByLectureId(userId);
    }
}
