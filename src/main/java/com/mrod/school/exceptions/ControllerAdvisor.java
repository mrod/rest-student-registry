//
//                              ASSIA, Inc.
//                               Copyright
//                     Confidential and Proprietary
//                         ALL RIGHTS RESERVED.
//
//      http://www.assia-inc.com, +1.650.654.3400
//
//      This software is provided under license and may be used, 
//      copied, distributed (with or without modification), or made 
//      available to the public, only in accordance with the terms
//      of such license.
//

package com.mrod.school.exceptions;

import java.time.LocalDateTime;
import java.util.Collections;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@ControllerAdvice
public class ControllerAdvisor extends ResponseEntityExceptionHandler {

    private static final Map<StatusCode, HttpStatus> STATUS_CODE_HTTP_STATUS_MAP = createMapping();

    private static Map<StatusCode, HttpStatus> createMapping() {
        Map<StatusCode, HttpStatus> map = new HashMap<>();
        map.put(StatusCode.STUDENT_NOT_FOUND, HttpStatus.NOT_FOUND);
        map.put(StatusCode.STUDENT_EMAIL_TAKEN, HttpStatus.CONFLICT);
        // TODO add others
        return Collections.unmodifiableMap(map);
    }

    @ExceptionHandler(SchoolException.class)
    public ResponseEntity<Object> handleException(SchoolException e) {
        Map<String, Object> body = new LinkedHashMap<>();
        body.put("timestamp", LocalDateTime.now());  // TODO parse this
        StatusCode statusCode = e.getStatusCode();
        body.put("statusCode", statusCode.getCode());
        body.put("message", e.getMessage());
        HttpStatus httpStatus = STATUS_CODE_HTTP_STATUS_MAP.getOrDefault(statusCode, HttpStatus.INTERNAL_SERVER_ERROR);
        return new ResponseEntity<>(body, httpStatus);
    }
}
