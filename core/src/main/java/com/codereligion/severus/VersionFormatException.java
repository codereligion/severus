package com.codereligion.severus;

public final class VersionFormatException extends RuntimeException {

    VersionFormatException(String message) {
        super(message);
    }

    VersionFormatException(Exception e) {
        super(e);
    }

    public VersionFormatException(String message, Throwable cause) {
        super(message, cause);
    }
    
}
