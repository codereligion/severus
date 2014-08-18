package com.codereligion.versions;

public final class VersionFormatException extends RuntimeException {

    VersionFormatException(String message) {
        super(message);
    }

    VersionFormatException(Exception e) {
        super(e);
    }
    
}
