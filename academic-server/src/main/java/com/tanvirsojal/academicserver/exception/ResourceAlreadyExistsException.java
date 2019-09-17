package com.tanvirsojal.academicserver.exception;

public class ResourceAlreadyExistsException extends Exception {
    public ResourceAlreadyExistsException(String message) {
        super(message + " already exists!");
    }
}
