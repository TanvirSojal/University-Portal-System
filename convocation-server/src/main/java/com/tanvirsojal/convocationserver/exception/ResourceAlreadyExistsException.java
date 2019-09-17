package com.tanvirsojal.convocationserver.exception;

public class ResourceAlreadyExistsException extends Exception {
    public ResourceAlreadyExistsException(String message) {
        super(message + " already exists!");
    }
}
