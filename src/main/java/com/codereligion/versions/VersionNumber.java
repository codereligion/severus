package com.codereligion.versions;

import com.google.common.base.Objects;
import com.google.common.primitives.Ints;

import javax.annotation.Nonnull;
import javax.annotation.concurrent.Immutable;

import static java.lang.Integer.parseInt;

@Immutable
public final class VersionNumber implements Identifier, Comparable<VersionNumber> {
    
    private final int value;

    private VersionNumber(int value) {
        this.value = value;
    }

    public int getValue() {
        return value;
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(value);
    }

    @Override
    public boolean equals(Object that) {
        return that instanceof VersionNumber && ((VersionNumber) that).getValue() == value;
    }

    @Override
    public String toString() {
        return String.valueOf(value);
    }

    @Override
    public int compareTo(@Nonnull VersionNumber that) {
        return Ints.compare(value, that.getValue());
    }
    
    public static VersionNumber valueOf(int value) {
        return new VersionNumber(value);
    }
    
    public static VersionNumber parse(String value) {
        return valueOf(parseInt(value));
    }

}
