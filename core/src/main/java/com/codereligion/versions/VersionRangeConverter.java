package com.codereligion.versions;

import com.google.common.base.Converter;
import com.google.common.collect.BoundType;
import com.google.common.collect.Range;

import javax.annotation.Nullable;
import javax.annotation.concurrent.Immutable;

import static com.google.common.collect.BoundType.OPEN;

@Immutable
final class VersionRangeConverter extends Converter<String, Range<Version>> {
    
    private final VersionPrecedence precedence;

    VersionRangeConverter(VersionPrecedence precedence) {
        this.precedence = precedence;
    }

    @Override
    protected Range<Version> doForward(String version) {
        return VersionRange.valueOf(version, precedence);
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
