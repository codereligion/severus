package com.codereligion.versions;

import com.google.common.base.Converter;
import com.google.common.base.Optional;
import com.google.common.collect.BoundType;
import com.google.common.collect.Range;

import javax.annotation.Nullable;
import javax.annotation.concurrent.Immutable;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static com.google.common.collect.BoundType.OPEN;

@Immutable
final class VersionRangeConverter extends Converter<String, Range<Version>> {
    
    private static final Pattern PATTERN = Pattern.compile("^([\\[\\(])(.*),(.*)([\\)\\]])$");
    
    private final VersionPrecedence precedence;

    VersionRangeConverter(VersionPrecedence precedence) {
        this.precedence = precedence;
    }

    @Override
    protected Range<Version> doForward(String range) {
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
            return Range.singleton(Version.valueOf(range, precedence));
        }
        // TODO throw VersionFormatException
    }

    private static Optional<Version> parse(String version, VersionPrecedence precedence) {
        if (version.isEmpty()) {
            return Optional.absent();
        } else {
            return Optional.of(Version.valueOf(version, precedence));
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

    @Override
    protected String doBackward(Range<Version> range) {
        if (range.hasLowerBound() && range.hasUpperBound()) {
            return render(range.lowerBoundType(), range.lowerEndpoint(), range.upperEndpoint(), range.upperBoundType());
        } else if (range.hasLowerBound()) {
            return render(range.lowerBoundType(), range.lowerEndpoint(), null, OPEN);
        } else if (range.hasUpperBound()) {
            return render(OPEN, null, range.upperEndpoint(), range.upperBoundType());
        } else {
            return render(OPEN, null, null, OPEN);
        }
    }
    
    private String render(BoundType lowerBoundType, @Nullable Version lowerEndpoint,
                          @Nullable Version upperEndpoint, BoundType upperBoundType) {
        return left(lowerBoundType) + toString(lowerEndpoint) + ", " + toString(upperEndpoint) + right(upperBoundType);
    }
    
    private String toString(@Nullable Version version) {
        return version == null ? "" : version.toString();
    }
    
    private char left(BoundType type) {
        switch (type) {
            case OPEN:
                return '(';
            case CLOSED:
                return '[';
            default:
                throw new UnsupportedOperationException("Unknown bound type: " + type);
        }
    }
    
    private char right(BoundType type) {
        switch (type) {
            case OPEN:
                return ')';
            case CLOSED:
                return ']';
            default:
                throw new UnsupportedOperationException("Unknown bound type: " + type);
        }
    }
    
}
