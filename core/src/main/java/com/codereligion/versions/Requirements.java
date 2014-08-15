package com.codereligion.versions;

import com.google.common.base.CharMatcher;

import java.math.BigInteger;

import static com.google.common.base.Preconditions.checkArgument;

final class Requirements {

    private static final CharMatcher ZERO = CharMatcher.is('0');

    private Requirements() {
        // utility class
    }

    static void checkNonNegative(BigInteger value) {
        checkArgument(value.compareTo(BigInteger.ZERO) >= 0,
                "Value must not be negative, but was [%s]", value);
    }

    static void checkNoLeadingZero(String value) {
        checkArgument("0".equals(value) || !ZERO.matches(value.charAt(0)),
                "Value must not start with a zero, but was [%s]", value);
    }

    static void checkNotEmpty(String value) {
        checkArgument(!value.isEmpty(),
                "Value must not be empty, but was [%s]", value);
    }

}
