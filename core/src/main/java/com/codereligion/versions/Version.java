package com.codereligion.versions;

import com.google.common.base.Converter;

import javax.annotation.Nonnull;
import javax.annotation.concurrent.Immutable;
import java.util.Objects;

// TODO serializable?
@Immutable
public abstract class Version implements Comparable<Version> {

    Version() {

    }

    public abstract VersionNumber getMajor();

    public abstract VersionNumber getMinor();

    public abstract VersionNumber getPatch();

    public abstract PreReleaseVersion getPreRelease();

    public abstract BuildMetadata getBuild();

    abstract VersionPrecedence getPrecedence();

    @Override
    public boolean equals(Object that) {
        if (this == that) {
            return true;
        } else if (that instanceof Version) {
            final Version other = (Version) that;
            return Objects.equals(getPrecedence(), other.getPrecedence()) &&
                    getPrecedence().equivalent(this, other);
        } else {
            return false;
        }
    }

    @Override
    public int hashCode() {
        return getPrecedence().hash(this);
    }

    @Override
    public int compareTo(@Nonnull Version version) {
        return getPrecedence().compare(this, version);
    }

    @Override
    public String toString() {
        return converter().reverse().convert(this);
    }

    public static Version valueOf(String version) {
        return converter().convert(version);
    }

    public static Version valueOf(final int major) {
        return builder().major(major).create();
    }

    public static Version valueOf(final int major, final int minor) {
        return builder().major(major).minor(minor).create();
    }

    public static Version valueOf(final int major, final int minor, final int patch) {
        return builder().major(major).minor(minor).patch(patch).create();
    }

    public static VersionBuilder builder() {
        return new VersionBuilder();
    }
    
    public static Converter<String, Version> converter() {
        return new VersionConverter();
    }

}