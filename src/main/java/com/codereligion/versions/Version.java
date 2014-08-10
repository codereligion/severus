package com.codereligion.versions;

import com.google.common.annotations.VisibleForTesting;
import com.google.common.base.Joiner;
import com.google.common.collect.FluentIterable;

import javax.annotation.Nonnull;
import javax.annotation.concurrent.Immutable;
import java.util.Objects;

@Immutable
public abstract class Version implements Comparable<Version> {

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
        return Versions.naturalOrdering().compare(this, version);
    }

    @Override
    public String toString() {
        final StringBuilder builder = new StringBuilder();
        Joiner.on('.').appendTo(builder, getMajorVersion(), getMinorVersion(), getPatchVersion());
        if (!FluentIterable.from(getPreReleaseVersion()).isEmpty()) {
            builder.append("-").append(getPreReleaseVersion());
        }
        if (!FluentIterable.from(getBuildMetadata()).isEmpty()) {
            builder.append("+").append(getBuildMetadata());
        }
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
                new DefaultVersionNumber(major),
                new DefaultVersionNumber(minor),
                new DefaultVersionNumber(patch),
                Versions.PreRelease.parse(preReleaseVersion),
                Versions.Metadata.parse(buildMetadata)
        );
    }

}
