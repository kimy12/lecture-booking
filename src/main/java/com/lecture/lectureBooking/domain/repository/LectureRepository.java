package com.lecture.lectureBooking.domain.repository;

import com.lecture.lectureBooking.domain.LectureBookingStatus;
import com.lecture.lectureBooking.domain.Lectures;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;

@Repository
public interface LectureRepository extends JpaRepository<Lectures, Long> {

    @Query("select l " +
            "from Lectures l " +
            "where DATE(l.lectureAt) = :lectureAt " +
            "and l.status= :status")
    List<Lectures> findLecturesAllBy(@Param("lectureAt") LocalDate lectureAt, LectureBookingStatus status);
}
