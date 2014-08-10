package com.codereligion.versions;

import com.google.common.base.Objects;
import com.google.common.primitives.Ints;

import javax.annotation.Nonnull;
import javax.annotation.concurrent.Immutable;

@Immutable
final class DefaultVersionNumber implements VersionNumber {
    
    private final int value;

    public DefaultVersionNumber(int value) {
        this.value = value;
    }

    @Override
    public int getValue() {
        return value;
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(value);
    }

    @Override
    public boolean equals(Object obj) {
        return obj instanceof VersionNumber && ((VersionNumber) obj).getValue() == value;
    }

    @Override
    public String toString() {
        return String.valueOf(value);
    }

    @Override
    public int compareTo(@Nonnull VersionNumber that) {
        return Ints.compare(value, that.getValue());
    }

}
