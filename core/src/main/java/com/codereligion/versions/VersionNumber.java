package com.codereligion.versions;

import javax.annotation.concurrent.Immutable;
import java.util.Objects;

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
        return new VersionNumber(value);
    }
    
    public static VersionNumber parse(String value) {
        return valueOf(parseInt(value));
    }

}
