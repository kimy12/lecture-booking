package com.lecture.lectureBooking.domain.repository;

import com.lecture.lectureBooking.domain.Lectures;
import jakarta.persistence.LockModeType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Lock;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Repository
public interface LectureRepository extends JpaRepository<Lectures, Long> {

    @Query("select l " +
            "from Lectures l " +
            "where DATE(l.lectureAt) = :lectureAt " +
            "and l.memberCount < 30")
    List<Lectures> findLecturesAllBy(@Param("lectureAt") LocalDate lectureAt);

    @Lock(LockModeType.PESSIMISTIC_WRITE)
    @Query("SELECT l FROM Lectures l WHERE l.id = :id")
    Optional<Lectures> findLectureByIdWithLock(@Param("id") Long id);

}
