package com.lecture.lectureBooking.domain;

import jakarta.persistence.*;
import lombok.Getter;

import java.time.LocalDateTime;
import java.util.List;

import static com.lecture.lectureBooking.domain.LectureBookingStatus.AVAILABLE;

@Entity
@Getter
public class Members {

    @Id
    @Column(name = "member_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @OneToMany(mappedBy = "member")
    private List<LectureMembers> lectureMembers;



}
