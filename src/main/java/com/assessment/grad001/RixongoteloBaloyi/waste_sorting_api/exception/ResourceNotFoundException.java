package com.assessment.grad001.RixongoteloBaloyi.waste_sorting_api.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

/**
 * Custom exception to indicate that a requested resource could not be found.
 * This exception is annotated with @ResponseStatus to automatically return
 * a 404 Not Found HTTP status when thrown in a controller.
 */
@ResponseStatus(HttpStatus.NOT_FOUND)
public class ResourceNotFoundException extends RuntimeException {

    public ResourceNotFoundException(String message) {
        super(message);
    }
}
