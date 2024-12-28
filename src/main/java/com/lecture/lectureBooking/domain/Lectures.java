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
                      LocalDateTime lectureAt,
                      LocalDateTime startAt,
                      LocalDateTime endAt,
                      int memberCount,
                      List<LectureMembers> members) {
        this.title = title;
        this.lecturer = lecturer;
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

    public void addMemberCount () {
        if(this.memberCount >= 30) {
            throw new IllegalArgumentException("신청인원이 초과된 강의입니다.");
        }
        this.memberCount += 1;
    }

    public void checkLectureSchedules(){
        if(this.lectureAt.isBefore(LocalDateTime.now())){
            throw new IllegalArgumentException("이미 종료한 강의입니다.");
        }

        if(this.startAt.isAfter(LocalDateTime.now()) || this.endAt.isBefore(LocalDateTime.now())){
            throw new IllegalArgumentException("강의 신청기간이 아닙니다.");
        }
    }

}
