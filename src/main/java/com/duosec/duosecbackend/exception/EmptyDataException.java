package com.duosec.duosecbackend.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

/**
 * User: Avinash Vijayvargiya
 * Date: 28-Nov-22
 * Time: 2:25 PM
 */
@ResponseStatus(value = HttpStatus.BAD_REQUEST)
public class EmptyDataException extends RuntimeException {

    public EmptyDataException(String message) {
        super(message);
    }

    public EmptyDataException(String message, Throwable cause) {
        super(message, cause);
    }

    public EmptyDataException(Throwable cause) {
        super(cause);
    }

    public EmptyDataException(String message, Throwable cause, boolean enableSuppression,
                              boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }

}

