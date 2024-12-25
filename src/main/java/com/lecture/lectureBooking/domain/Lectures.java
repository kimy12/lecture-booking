package com.lecture.lectureBooking.domain;

import com.lecture.lectureBooking.presentation.dto.LectureResponseDto;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;
import java.util.ArrayList;
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

    @OneToMany(mappedBy = "lecture", cascade = CascadeType.ALL)
    @Setter
    private List<LectureMembers> members = new ArrayList<>();

    @Builder
    private Lectures (String title,
                      String lecturer,
                      LectureBookingStatus status,
                      LocalDateTime lectureAt,
                      LocalDateTime startAt,
                      LocalDateTime endAt,
                      int memberCount,
                      List<LectureMembers> members) {
        this.title = title;
        this.lecturer = lecturer;
        this.status = status;
        this.lectureAt = lectureAt;
        this.startAt = startAt;
        this.endAt = endAt;
        this.memberCount = memberCount;
        this.members = members;
    }

    public void addMember(LectureMembers member) {
        this.members.add(member);
        member.setLecture(this);
    }

}
