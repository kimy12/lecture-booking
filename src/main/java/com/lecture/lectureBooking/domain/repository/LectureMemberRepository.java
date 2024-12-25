package com.lecture.lectureBooking.domain.repository;

import com.lecture.lectureBooking.domain.LectureMembers;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface LectureMemberRepository extends JpaRepository<LectureMembers, Long> {

    @Query("select lm " +
            "from LectureMembers lm " +
            "join fetch lm.lecture l " +
            "where lm.userId= :userId")
    List<LectureMembers> findByLectureId(@Param("userId")Long userId);
}
