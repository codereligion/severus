package com.codereligion.versions;

import javax.annotation.concurrent.Immutable;
import java.util.Objects;

import static com.codereligion.versions.Requirements.checkNoLeadingZero;
import static com.codereligion.versions.Requirements.checkNonNegative;
import static com.codereligion.versions.Requirements.checkNotEmpty;
import static com.google.common.base.Preconditions.checkNotNull;
import static java.lang.Integer.parseInt;

@Immutable
public final class VersionNumber implements Identifier<Integer> {
    
    private final int value;

    private VersionNumber(int value) {
        this.value = value;
    }

    @Override
    public Integer getValue() {
        return value;
    }

    @Override
    public boolean equals(Object that) {
        return that instanceof VersionNumber && ((VersionNumber) that).getValue() == value;
    }

    @Override
    public int hashCode() {
        return Objects.hash(value);
    }

    @Override
    public String toString() {
        return String.valueOf(value);
    }

    public static VersionNumber valueOf(int value) {
        return new VersionNumber(checkNonNegative(value));
    }
    
    public static VersionNumber parse(String value) {
        checkNotNull(value, "Value");
        checkNotEmpty(value);
        checkNoLeadingZero(value);
        return valueOf(parseInt(value));
    }

}
