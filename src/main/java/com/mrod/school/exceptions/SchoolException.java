package com.mrod.school.exceptions;

public class SchoolException extends RuntimeException {

    private final StatusCode statusCode;

    public SchoolException(StatusCode statusCode) {
        this.statusCode = statusCode;
    }

    public StatusCode getStatusCode() {
        return statusCode;
    }
}
