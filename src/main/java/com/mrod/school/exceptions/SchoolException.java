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

public class SchoolException extends RuntimeException {

    private final StatusCode statusCode;

    public SchoolException(StatusCode statusCode) {
        this.statusCode = statusCode;
    }

    @Override
    public String getMessage() {
        return statusCode.getMessage();
    }

    public StatusCode getStatusCode() {
        return statusCode;
    }
}
