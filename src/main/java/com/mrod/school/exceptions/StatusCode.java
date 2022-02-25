package com.mrod.school.exceptions;

public enum StatusCode {

    SUCCESS(0),
    STUDENT_NOT_FOUND(1),
    COURSE_NOT_FOUND(2),
    STUDENT_EMAIL_TAKEN(3);

    private final int code;

    StatusCode(int code) {
        this.code = code;
    }

    public int getCode() {
        return code;
    }

}
