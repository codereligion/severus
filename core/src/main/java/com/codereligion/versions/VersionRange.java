package com.codereligion.versions;

import com.google.common.base.Converter;
import com.google.common.base.Optional;
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
        return valueOf(range, VersionPrecedence.NATURAL);
    }
    
    public static Range<Version> valueOf(String range, VersionPrecedence precedence) throws VersionFormatException {
        // TODO reuse pattern from VersionBuilder?
        // TODO add support for partial versions, e.g. [1.5,2) -> [1.5.0,2.0.0)
        // TODO add tests for pre-release
        // TODO add support for precedence + tests for builds

        final Matcher matcher = PATTERN.matcher(range);
        
        if (matcher.matches()) {
            final BoundType lowerType = parse(matcher.group(1).charAt(0));
            final Optional<Version> lower = parse(matcher.group(2), precedence);
            final Optional<Version> upper = parse(matcher.group(3), precedence);
            final BoundType upperType = parse(matcher.group(4).charAt(0));

            if (lower.isPresent() && upper.isPresent()) {
                return Range.range(lower.get(), lowerType, upper.get(), upperType);
            } else if (lower.isPresent()) {
                return Range.downTo(lower.get(), lowerType);
            } else if (upper.isPresent()) {
                return Range.upTo(upper.get(), upperType);
            } else {
                return Range.all();
            }
        } else {
            // TODO verify first
            return Range.singleton(Version.builder().parse(range).precendence(precedence).create());
        }
        // TODO throw VersionFormatException
    }

    private static Optional<Version> parse(String version, VersionPrecedence precedence) {
        if (version.isEmpty()) {
            return Optional.absent();
        } else {
            return Optional.of(Version.builder().parse(version).precendence(precedence).create());
        }
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
                throw new UnsupportedOperationException("Unsupported bound type: " + c);
        }
    }

    public static Converter<String, Range<Version>> converter() {
        return converter(VersionPrecedence.NATURAL);
    }

    public static Converter<String, Range<Version>> converter(VersionPrecedence precedence) {
        return new VersionRangeConverter(precedence);
    }
    
}
