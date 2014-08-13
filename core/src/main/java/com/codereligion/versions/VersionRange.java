package com.codereligion.versions;

import com.google.common.collect.Range;

public final class VersionRange {

    private VersionRange() {
        // static factory
    }
    
    public static Range<Version> valueOf(String range) throws VersionFormatException {
        throw new UnsupportedOperationException();
    }
    
    public static Range<Version> valueOf(String range, VersionPrecedence precedence) throws VersionFormatException {
        throw new UnsupportedOperationException();
    }
    
}
