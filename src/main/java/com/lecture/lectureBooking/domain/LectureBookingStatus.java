package com.lecture.lectureBooking.domain;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum LectureBookingStatus {

    AVAILABLE("예약 가능"),
    UPCOMING("예약 예정"),
    CLOSED("예약 마감");

    private final String status;
}
