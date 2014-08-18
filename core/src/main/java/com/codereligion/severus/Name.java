package com.codereligion.severus;

import javax.annotation.concurrent.Immutable;
import java.util.Objects;

import static com.codereligion.severus.Requirements.checkNotEmpty;
import static com.google.common.base.Preconditions.checkNotNull;

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
        if (this == that) {
            return true;
        } else if (that instanceof Name) {
            final Name other = (Name) that;
            return value.equals(other.getValue());
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
        return value;
    }

    public static Name valueOf(final String value) {
        checkNotNull(value, "Value");
        checkNotEmpty(value);
        
        return new Name(value);
    }
    
}
