package me.bxbc;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

/**
 * Author: BI XI
 * Date 2021/2/2
 */


@ResponseStatus(HttpStatus.NOT_FOUND)
public class notFoundException extends RuntimeException {
    public notFoundException() {
    }

    public notFoundException(String message) {
        super(message);
    }

    public notFoundException(String message, Throwable cause) {
        super(message, cause);
    }
}
