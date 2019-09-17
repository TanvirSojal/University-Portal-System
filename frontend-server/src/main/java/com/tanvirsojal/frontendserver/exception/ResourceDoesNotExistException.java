package com.tanvirsojal.frontendserver.exception;

public class ResourceDoesNotExistException extends Exception {
    public ResourceDoesNotExistException(String message) {
        super(message + " does not exist!");
    }
}
