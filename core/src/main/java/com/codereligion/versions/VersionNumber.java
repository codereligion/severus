package com.codereligion.versions;

import javax.annotation.concurrent.Immutable;
import java.math.BigInteger;
import java.util.Objects;

import static com.codereligion.versions.Requirements.checkNoLeadingZero;
import static com.codereligion.versions.Requirements.checkNonNegative;
import static com.codereligion.versions.Requirements.checkNotEmpty;
import static com.google.common.base.Preconditions.checkNotNull;

@Immutable
public final class VersionNumber implements Identifier<BigInteger> {
    
    private final BigInteger value;

    private VersionNumber(BigInteger value) {
        this.value = value;
    }

    @Override
    public BigInteger getValue() {
        return value;
    }

    @Override
    public boolean equals(Object that) {
        if (this == that) {
            return true;
        } else if (that instanceof VersionNumber) {
            final VersionNumber other = (VersionNumber) that;
            return value.compareTo(other.getValue()) == 0;
        } else {
            return false;
        }
    }

    @Override
    public int hashCode() {
        return Objects.hash(value);
    }

    @Override
    public String toString() {
        return String.valueOf(value);
    }

    public static VersionNumber valueOf(long value) {
        return valueOf(BigInteger.valueOf(value));
    }
    
    public static VersionNumber valueOf(String value) {
        checkNotNull(value, "Value");
        checkNotEmpty(value);
        checkNoLeadingZero(value);
        return valueOf(new BigInteger(value));
    }

    public static VersionNumber valueOf(BigInteger value) {
        checkNonNegative(value);
        return new VersionNumber(value);
    }


}
