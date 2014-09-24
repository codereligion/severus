package com.codereligion.severus;

import com.google.common.base.Function;
import com.google.common.base.Joiner;
import com.google.common.collect.BoundType;
import com.google.common.collect.Range;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import javax.annotation.concurrent.Immutable;
import java.util.Objects;

import static com.google.common.collect.BoundType.OPEN;
import static com.google.common.collect.Iterables.transform;

@Immutable
final class VersionRangeWriter implements Writer<VersionRange>, NullHostileFunction<Range<Version>, String> {

    @Override
    public String write(VersionRange range) {
        return Joiner.on(',').join(transform(range.asRanges(), this));
    }

    @Override
    public String apply(Range<Version> range) {
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
    
    private String render(BoundType lowerBound, @Nullable Version lower,
                          @Nullable Version upper, BoundType upperBound) {

        if (lower != null && Objects.equals(lower, upper)) {
            return left(lowerBound) + lower.toString() + right(upperBound);
        }

        return left(lowerBound) + toString(lower) + "," + toString(upper) + right(upperBound);
    }

    @Nonnull
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
