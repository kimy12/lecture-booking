package com.lecture.lectureBooking.domain.repository;

import com.lecture.lectureBooking.domain.LectureMembers;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface LectureMemberRepository extends JpaRepository<LectureMembers, Long> {

    List<LectureMembers> findByLectureId(Long userId);
}
