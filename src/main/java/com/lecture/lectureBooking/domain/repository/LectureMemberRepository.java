package com.lecture.lectureBooking.domain.repository;

import com.lecture.lectureBooking.domain.LectureMembers;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface LectureMemberRepository extends JpaRepository<LectureMembers, Long> {

    @Query("select lm " +
            "from LectureMembers lm " +
            "join fetch lm.lecture l " +
            "where lm.userId= :userId")
    List<LectureMembers> findByUserId(@Param("userId")Long userId);

    Optional<LectureMembers> findByLectureIdAndUserId(long lectureId, long userId);
}
