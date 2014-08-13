package com.codereligion.versions;

import com.google.common.base.CharMatcher;

import static com.google.common.base.Preconditions.checkNotNull;

public final class Identifiers {

    private static final CharMatcher DIGIT = CharMatcher.inRange('0', '9');

    private Identifiers() {
        // static factory
    }

    public static Identifier valueOf(final String value) {
        checkNotNull(value, "Value");
        
        if (DIGIT.matchesAllOf(value)) {
            return VersionNumber.valueOf(value);
        } else {
            return Name.valueOf(value);
        }
    }

}
