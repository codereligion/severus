package com.codereligion.versions;

import com.google.common.base.Objects;

import javax.annotation.concurrent.Immutable;

@Immutable
final class Name implements Identifier {

    private final String value;

    public Name(String value) {
        this.value = value;
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(value);
    }

    @Override
    public boolean equals(Object that) {
        return that instanceof Name && Objects.equal(that.toString(), value);
    }

    @Override
    public String toString() {
        return value;
    }
    
}
