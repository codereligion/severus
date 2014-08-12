package com.codereligion.versions;

import javax.annotation.concurrent.Immutable;

@Immutable
final class ConcreteVersion extends Version {
    
    private final VersionNumber major;
    private final VersionNumber minor;
    private final VersionNumber patch;
    private final PreReleaseVersion preRelease;
    private final BuildMetadata buildMetadata;

    public ConcreteVersion(VersionNumber major, VersionNumber minor, VersionNumber patch, PreReleaseVersion preRelease, BuildMetadata buildMetadata) {
        this.major = major;
        this.minor = minor;
        this.patch = patch;
        this.preRelease = preRelease;
        this.buildMetadata = buildMetadata;
    }

    @Override
    public VersionNumber getMajor() {
        return major;
    }

    @Override
    public VersionNumber getMinor() {
        return minor;
    }

    @Override
    public VersionNumber getPatch() {
        return patch;
    }

    @Override
    public PreReleaseVersion getPreRelease() {
        return preRelease;
    }

    @Override
    public BuildMetadata getBuildMetadata() {
        return buildMetadata;
    }

}
