package com.mrod.school.exceptions;

import java.time.LocalDateTime;
import java.util.Collections;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;

import org.springframework.context.MessageSource;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@ControllerAdvice
public class ControllerAdvisor extends ResponseEntityExceptionHandler {

    private static final Map<StatusCode, HttpStatus> STATUS_CODE_HTTP_STATUS_MAP = createMapping();

    private final MessageSource messageSource;

    public ControllerAdvisor(MessageSource messageSource) {
        this.messageSource = messageSource;
    }

    private static Map<StatusCode, HttpStatus> createMapping() {
        Map<StatusCode, HttpStatus> map = new HashMap<>();
        map.put(StatusCode.STUDENT_NOT_FOUND, HttpStatus.NOT_FOUND);
        map.put(StatusCode.STUDENT_EMAIL_TAKEN, HttpStatus.CONFLICT);
        // TODO add others
        return Collections.unmodifiableMap(map);
    }

    @ExceptionHandler(SchoolException.class)
    public ResponseEntity<Object> handleException(SchoolException e, WebRequest request) {
        Map<String, Object> body = new LinkedHashMap<>();
        body.put("timestamp", LocalDateTime.now());  // TODO parse this
        StatusCode statusCode = e.getStatusCode();
        body.put("statusCode", statusCode.getCode());
        body.put("message", messageSource.getMessage("status_code" + statusCode.getCode(), null, request.getLocale()));
        HttpStatus httpStatus = STATUS_CODE_HTTP_STATUS_MAP.getOrDefault(statusCode, HttpStatus.INTERNAL_SERVER_ERROR);
        return new ResponseEntity<>(body, httpStatus);
    }
}
