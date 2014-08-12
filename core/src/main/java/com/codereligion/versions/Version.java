package com.codereligion.versions;

import javax.annotation.Nonnull;
import javax.annotation.concurrent.Immutable;
import java.util.Objects;

import static com.google.common.base.Joiner.on;
import static com.google.common.base.Preconditions.checkNotNull;
import static com.google.common.collect.Iterables.isEmpty;

@Immutable
public abstract class Version implements Comparable<Version> {

    Version() {
        
    }

    public abstract VersionNumber getMajor();

    public abstract VersionNumber getMinor();

    public abstract VersionNumber getPatch();

    public abstract PreReleaseVersion getPreRelease();

    public abstract BuildMetadata getBuildMetadata();

    @Override
    public boolean equals(Object that) {
        if (this == that) {
            return true;
        } else if (that instanceof Version) {
            final Version other = (Version) that;
            return Objects.equals(getMajor(), other.getMajor()) &&
                    Objects.equals(getMinor(), other.getMinor()) &&
                    Objects.equals(getPatch(), other.getPatch()) &&
                    Objects.equals(getPreRelease(), other.getPreRelease());
        } else {
            return false;
        }
    }

    @Override
    public int hashCode() {
        return Objects.hash(getMajor(), getMinor(), getPatch(), getPreRelease());
    }

    @Override
    public int compareTo(@Nonnull Version version) {
        // TODO singleton?
        return new VersionPrecedence().compare(this, version);
    }
    
    @Override
    public String toString() {
        final StringBuilder builder = new StringBuilder();
        on('.').appendTo(builder, getMajor(), getMinor(), getPatch());
        append(builder, "-", getPreRelease());
        append(builder, "+", getBuildMetadata());
        return builder.toString();
    }

    private <I extends Identifier> void append(StringBuilder builder, String prefix, Iterable<I> tuple) {
        if (isEmpty(tuple)) {
            return;
        }

        builder.append(prefix).append(tuple);
    }

    public static Version parse(String version) {
        return VersionParser.parse(checkNotNull(version, "Version"));
    }
    
    public static Version valueOf(final int major) {
        return builder().major(major).build();
    }

    public static Version valueOf(final int major, final int minor) {
        return builder().major(major).minor(minor).build();
    }

    public static Version valueOf(final int major, final int minor, final int patch) {
        return builder().major(major).minor(minor).patch(patch).build();
    }

    public static VersionBuilder builder() {
        return new VersionBuilder();
    }

}
