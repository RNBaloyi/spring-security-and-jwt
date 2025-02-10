package com.assessment.grad001.RixongoteloBaloyi.waste_sorting_api.exception;

/**
 * Custom exception class to handle cases where a waste category is not found.
 * This exception is thrown when an attempt is made to access or reference a
 * waste category that does not exist in the system.
 */
public class CategoryNotFoundException extends RuntimeException {

    public CategoryNotFoundException(String message) {
        super(message);
    }
}
