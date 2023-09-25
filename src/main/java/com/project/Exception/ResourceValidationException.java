package com.project.Exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(code = HttpStatus.BAD_REQUEST)
public class ResourceValidationException extends RuntimeException{
    public ResourceValidationException(String mssg) {
        super(mssg);
    }
}
