package com.codereligion.versions;

import com.google.common.collect.BoundType;
import com.google.common.collect.Range;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public final class VersionRange {
    
    private static final Pattern PATTERN = Pattern.compile("^([\\[\\(])(.*),(.*)([\\)\\]])$");

    private VersionRange() {
        // static factory
    }
    
    public static Range<Version> valueOf(String range) throws VersionFormatException {
        // TODO reuse pattern from VersionBuilder?
        // TODO add support for partial versions, e.g. [1.5,2) -> [1.5.0,2.0.0)

        final Matcher matcher = PATTERN.matcher(range);
        
        if (matcher.matches()) {
            final BoundType lowerType = parse(matcher.group(1).charAt(0));
            final Version lower = parse(matcher.group(2));
            final Version upper = parse(matcher.group(3));
            final BoundType upperType = parse(matcher.group(4).charAt(0));

            if (lower == null && upper == null) {
                return Range.all();
            } else if (lower == null) {
                return Range.upTo(upper, upperType);
            } else if (upper == null) {
                return Range.downTo(lower, lowerType);
            } else {
                return Range.range(lower, lowerType, upper, upperType);
            }
        } else {
            return Range.singleton(Version.valueOf(range));
        }
    }
    
    private static Version parse(String version) {
        return version.isEmpty() ? null : Version.valueOf(version);
    }
    
    private static BoundType parse(char c) {
        switch (c) {
            case '(':
            case ')':
                return BoundType.OPEN;
            case '[':
            case ']':
                return BoundType.CLOSED;
            default:
                throw new UnsupportedOperationException("Unknown bound type: " + c);
        }
    }
    
    public static Range<Version> valueOf(String range, VersionPrecedence precedence) throws VersionFormatException {
        throw new UnsupportedOperationException();
    }
    
    // TODO converter
    
}
