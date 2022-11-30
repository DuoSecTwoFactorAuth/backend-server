package com.duosec.duosecbackend.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

/**
 * User: Avinash Vijayvargiya
 * Date: 28-Nov-22
 * Time: 2:34 PM
 */
@ResponseStatus(value = HttpStatus.BAD_REQUEST)
public class NullDataException extends RuntimeException {

    public NullDataException(String message) {
        super(message);
    }

    public NullDataException(String message, Throwable cause) {
        super(message, cause);
    }

    public NullDataException(Throwable cause) {
        super(cause);
    }

    public NullDataException(String message, Throwable cause, boolean enableSuppression,
                             boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }

}
