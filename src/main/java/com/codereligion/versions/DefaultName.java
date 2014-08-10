package com.codereligion.versions;

import com.google.common.base.Objects;

import javax.annotation.concurrent.Immutable;

@Immutable
final class DefaultName implements Name {

    private final String value;

    public DefaultName(String value) {
        this.value = value;
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(value);
    }

    @Override
    public boolean equals(Object obj) {
        return obj instanceof Name && Objects.equal(obj.toString(), value);
    }

    @Override
    public String toString() {
        return value;
    }
}
