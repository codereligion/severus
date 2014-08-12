package com.codereligion.versions;

import com.google.common.base.CharMatcher;

import static com.google.common.base.Preconditions.checkArgument;

final class Requirements {

    private static final CharMatcher ZERO = CharMatcher.is('0');

    private Requirements() {
        // utility class
    }

    static int checkNonNegative(int value) {
        checkArgument(value >= 0, 
                "Value must not be negative, but was [%s]", value);
        return value;
    }

    static String checkNoLeadingZero(String value) {
        checkArgument("0".equals(value) || !ZERO.matches(value.charAt(0)),
                "Value must not start with a zero, but was [%s]", value);
        return value;
    }

    static String checkNotEmpty(String value) {
        checkArgument(!value.isEmpty(),
                "Value must not be empty, but was [%s]", value);
        return value;
    }

}
