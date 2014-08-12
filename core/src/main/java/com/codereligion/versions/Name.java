package com.codereligion.versions;

import javax.annotation.concurrent.Immutable;
import java.util.Objects;

@Immutable
public final class Name implements Identifier<String> {

    private final String value;

    private Name(String value) {
        this.value = value;
    }

    @Override
    public Comparable<String> getValue() {
        return value;
    }

    @Override
    public boolean equals(Object that) {
        return that instanceof Name && Objects.equals(that.toString(), value);
    }

    @Override
    public int hashCode() {
        return Objects.hash(value);
    }

    @Override
    public String toString() {
        return value;
    }

    public static Name valueOf(final String value) {
        return new Name(value);
    }
    
}
