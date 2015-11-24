package edu.sjsu.cmpe275.tagit.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.BAD_REQUEST)
public class EntityNotFound extends RuntimeException {

    private static final long serialVersionUID = 1L;

    public EntityNotFound() {
    }

    public EntityNotFound(String arg0) {
        super(arg0);

    }
}