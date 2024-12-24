package com.lecture.lectureBooking.domain;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@NoArgsConstructor(access =  AccessLevel.PROTECTED)
@Entity
@Getter
public class LectureMembers {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    private long userId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "lecture_id")
    private Lectures lecture;

    private LocalDateTime createdAt;

    @Builder
    private LectureMembers(long id, long userId, Lectures lecture, LocalDateTime createdAt) {
        this.id = id;
        this.userId = userId;
        this.lecture = lecture;
        this.createdAt = createdAt;
    }
}
