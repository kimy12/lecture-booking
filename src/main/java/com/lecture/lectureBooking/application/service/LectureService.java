package com.lecture.lectureBooking.application.service;

import com.lecture.lectureBooking.domain.LectureMembers;
import com.lecture.lectureBooking.domain.Lectures;
import com.lecture.lectureBooking.domain.repository.LectureMemberRepository;
import com.lecture.lectureBooking.domain.repository.LectureRepository;
import com.lecture.lectureBooking.presentation.dto.LectureRequestDto;
import jakarta.persistence.EntityManager;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.support.TransactionSynchronizationManager;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

@Transactional(readOnly = true)
@RequiredArgsConstructor
@Service
public class LectureService {

    private final LectureRepository lectureRepository;
    private final LectureMemberRepository lectureMemberRepository;
    private final EntityManager entityManager;

    public List<Lectures> getAllAvailableLectures(LocalDate lectureAt) {
        return lectureRepository.findLecturesAllBy(lectureAt);
    }

    public List<LectureMembers> getAllBookedLecturesByUser(long userId) {
        return lectureMemberRepository.findByUserId(userId);
    }

    @Transactional(isolation = Isolation.READ_COMMITTED)
    public LectureMembers bookLecture (LectureRequestDto.BookLectureForm request){
        Lectures lectures = lectureRepository.findLectureByIdWithLock(request.lectureId())
                .orElseThrow(() -> new IllegalArgumentException("존재하지 않는 강의 입니다."));

        lectureMemberRepository.findByLectureIdAndUserId(request.lectureId(), request.userId())
                .ifPresent(lectureMember -> {
                    throw new IllegalArgumentException("이미 신청완료한 강의 입니다.");
                });

        lectures.checkLectureSchedules();

        lectures.addMemberCount();

        LectureMembers bookLecturerInfo = LectureMembers.builder()
                .lecture(lectures)
                .userId(request.userId())
                .createdAt(LocalDateTime.now())
                .build();
        lectures.addMember(bookLecturerInfo);

        return  bookLecturerInfo;
    }

}
