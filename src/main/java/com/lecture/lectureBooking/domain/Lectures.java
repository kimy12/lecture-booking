package com.lecture.lectureBooking.domain;

import com.lecture.lectureBooking.presentation.dto.LectureResponseDto;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;


@NoArgsConstructor(access =  AccessLevel.PROTECTED)
@Getter
@Entity
public class Lectures {

    @Id
    @Column(name = "lecture_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    private String title;

    private String lecturer;

    @Enumerated(EnumType.STRING)
    private LectureBookingStatus status;

    private int memberCount;

    private LocalDateTime lectureAt;

    private LocalDateTime startAt;

    private LocalDateTime endAt;

    @OneToMany(mappedBy = "lecture")
    private List<LectureMembers> members;

    @Builder
    private Lectures (String title, String lecturer, LectureBookingStatus status, LocalDateTime lectureAt, LocalDateTime startAt, LocalDateTime endAt, int memberCount) {
        this.title = title;
        this.lecturer = lecturer;
        this.status = status;
        this.lectureAt = lectureAt;
        this.startAt = startAt;
        this.endAt = endAt;
        this.memberCount = memberCount;
    }

}
