package com.tanvirsojal.studentserver.exception;

public class ResourceAlreadyExistsException extends Exception {
    public ResourceAlreadyExistsException(String message) {
        super(message + " already exists!");
    }
}
