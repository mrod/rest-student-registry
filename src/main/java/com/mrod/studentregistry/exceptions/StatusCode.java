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

package com.mrod.studentregistry.exceptions;

public enum StatusCode {

    SUCCESS(0, "Success"),
    STUDENT_NOT_FOUND(1, "No student found with the provided criteria"),
    STUDENT_EMAIL_TAKEN(2, "Student email is taken");

    private final int code;
    private final String message;

    StatusCode(int code, String message) {

        this.code = code;
        this.message = message;
    }

    public int getCode() {
        return code;
    }

    public String getMessage() {
        return message;
    }


}
