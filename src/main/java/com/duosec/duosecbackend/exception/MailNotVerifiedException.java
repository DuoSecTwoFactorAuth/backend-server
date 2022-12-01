package com.duosec.duosecbackend.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

/**
 * User: Avinash Vijayvargiya
 * Date: 28-Nov-22
 * Time: 3:06 PM
 */
@ResponseStatus(value = HttpStatus.BAD_REQUEST)
public class MailNotVerifiedException extends RuntimeException {
    public MailNotVerifiedException(String message) {
        super(message);
    }

    public MailNotVerifiedException(String message, Throwable cause) {
        super(message, cause);
    }

    public MailNotVerifiedException(Throwable cause) {
        super(cause);
    }

    public MailNotVerifiedException(String message, Throwable cause, boolean enableSuppression,
                                    boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
