package com.project.Exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(code = HttpStatus.CONFLICT)
public class DuplicateResource extends RuntimeException {
    public DuplicateResource(String mssg) {
        super(mssg);
    }
}
