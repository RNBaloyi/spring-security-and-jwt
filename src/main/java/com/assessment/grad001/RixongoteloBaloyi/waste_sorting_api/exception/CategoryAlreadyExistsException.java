package com.assessment.grad001.RixongoteloBaloyi.waste_sorting_api.exception;

/**
 * Custom exception class to handle cases where a waste category already exists.
 * This exception is thrown when an attempt is made to create a waste category
 * that already exists in the system.
 */
public class CategoryAlreadyExistsException extends RuntimeException {

    public CategoryAlreadyExistsException(String message) {
        super(message);
    }
}
