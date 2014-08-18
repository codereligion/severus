package com.codereligion.severus;

import com.google.common.base.Converter;
import com.google.common.collect.ImmutableRangeSet;
import com.google.common.collect.RangeSet;

import javax.annotation.Nullable;
import javax.annotation.concurrent.Immutable;
import java.io.Serializable;
import java.util.Objects;

@Immutable
public final class VersionRange extends ForwardingRangeSet<Version> implements Serializable {

    private static final VersionRange EMPTY = new VersionRange();
    
    private final ImmutableRangeSet<Version> rangeSet;

    public VersionRange() {
        this.rangeSet = ImmutableRangeSet.of();
    }

    VersionRange(ImmutableRangeSet<Version> rangeSet) {
        this.rangeSet = rangeSet;
    }

    @Override
    protected RangeSet<Version> delegate() {
        return rangeSet;
    }

    @Override
    public boolean equals(@Nullable Object that) {
        if (this == that) {
            return true;
        } else if (that instanceof VersionRange) {
            final VersionRange other = (VersionRange) that;
            return Objects.equals(rangeSet, other.rangeSet);
        } else {
            return false;
        }
    }

    @Override
    public int hashCode() {
        return Objects.hash(rangeSet);
    }

    @Override
    public String toString() {
        return converter().reverse().convert(this);
    }

    public static VersionRange valueOf(String range) throws VersionFormatException {
        return converter().convert(range);
    }

    public static VersionRange valueOf(String range, VersionPrecedence precedence) throws VersionFormatException {
        return converter(precedence).convert(range);
    }

    public static Converter<String, VersionRange> converter() {
        return converter(VersionPrecedence.NATURAL);
    }

    public static Converter<String, VersionRange> converter(VersionPrecedence precedence) {
        return new VersionRangeConverter(precedence);
    }

    public static VersionRange empty() {
        return EMPTY;
    }
    
}
