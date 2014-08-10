package com.codereligion.versions;

import com.google.common.annotations.VisibleForTesting;

import javax.annotation.Nonnull;
import javax.annotation.concurrent.Immutable;
import java.util.Objects;

import static com.google.common.base.Joiner.on;
import static com.google.common.collect.FluentIterable.from;
import static com.google.common.collect.Iterables.isEmpty;

@Immutable
public abstract class Version implements Comparable<Version> {
    
    Version() {
        
    }

    public abstract VersionNumber getMajorVersion();

    public abstract VersionNumber getMinorVersion();

    public abstract VersionNumber getPatchVersion();

    public abstract PreReleaseVersion getPreReleaseVersion();

    public abstract BuildMetadata getBuildMetadata();

    @Override
    public boolean equals(Object that) {
        if (this == that) {
            return true;
        } else if (that instanceof Version) {
            final Version other = (Version) that;
            return Objects.equals(getMajorVersion(), other.getMajorVersion()) &&
                    Objects.equals(getMinorVersion(), other.getMinorVersion()) &&
                    Objects.equals(getPatchVersion(), other.getPatchVersion()) &&
                    Objects.equals(getPreReleaseVersion(), other.getPreReleaseVersion());
        } else {
            return false;
        }
    }

    @Override
    public int hashCode() {
        return Objects.hash(getMajorVersion(), getMinorVersion(), getPatchVersion(), getPreReleaseVersion());
    }

    @Override
    public int compareTo(@Nonnull Version version) {
        // TODO singleton?
        return new VersionPrecedence().compare(this, version);
    }
    
    private <I extends Identifier> void append(StringBuilder builder, String prefix, Iterable<I> tuple) {
        if (isEmpty(tuple)) {
            return;
        }

        builder.append(prefix).append(tuple);
    }

    @Override
    public String toString() {
        final StringBuilder builder = new StringBuilder();
        on('.').appendTo(builder, getMajorVersion(), getMinorVersion(), getPatchVersion());
        append(builder, "-", getPreReleaseVersion());
        append(builder, "+", getBuildMetadata());
        return builder.toString();
    }

    public static Version valueOf(final String version) {
        return new VersionParser().parse(version);
    }

    public static Version valueOf(final int major) {
        return valueOf(major, 0);
    }

    public static Version valueOf(final int major, final int minor) {
        return valueOf(major, minor, 0);
    }

    public static Version valueOf(final int major, final int minor, final int patch) {
        return valueOf(major, minor, patch, "");
    }

    @VisibleForTesting
    static Version valueOf(final int major, final int minor, final int patch,
                           final String preReleaseVersion) {
        return valueOf(major, minor, patch, preReleaseVersion, "");
    }

    @VisibleForTesting
    static Version valueOf(final int major, final int minor, final int patch,
                           final String preReleaseVersion, final String buildMetadata) {
        return new ConcreteVersion(
                VersionNumber.valueOf(major),
                VersionNumber.valueOf(minor),
                VersionNumber.valueOf(patch),
                PreReleaseVersion.parse(preReleaseVersion),
                BuildMetadata.parse(buildMetadata)
        );
    }
    
    public static VersionBuilder builder() {
        return new VersionBuilder();
    }

}
