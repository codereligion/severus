package com.codereligion.versions;

import static com.google.common.base.Preconditions.checkNotNull;

public final class VersionBuilder {
    
    private VersionNumber major = VersionNumber.valueOf(0);
    private VersionNumber minor = VersionNumber.valueOf(0);
    private VersionNumber patch = VersionNumber.valueOf(0);
    private PreReleaseVersion preRelease = PreReleaseVersion.empty();
    private BuildMetadata metadata = BuildMetadata.empty();
    
    public VersionBuilder major(int major) {
        return major(VersionNumber.valueOf(major));
    }
    
    public VersionBuilder major(String major) {
        return major(VersionNumber.parse(major));
    }
    
    public VersionBuilder major(VersionNumber major) {
        this.major = checkNotNull(major, "Major");
        return this;
    }
    
    public VersionBuilder minor(int minor) {
        return minor(VersionNumber.valueOf(minor));
    }
    
    public VersionBuilder minor(String minor) {
        return minor(VersionNumber.parse(minor));
    }
    
    public VersionBuilder minor(VersionNumber minor) {
        this.minor = checkNotNull(minor, "Minor");
        return this;
    }
    
    public VersionBuilder patch(int patch) {
        return patch(VersionNumber.valueOf(patch));
    }
    
    public VersionBuilder patch(String patch) {
        return patch(VersionNumber.parse(patch));
    }
    
    public VersionBuilder patch(VersionNumber patch) {
        this.patch = checkNotNull(patch, "Patch");
        return this;
    }
    
    public VersionBuilder preRelease(String version) {
        return preRelease(PreReleaseVersion.parse(version));
    }
    
    public VersionBuilder preRelease(PreReleaseVersion preRelease) {
        this.preRelease = checkNotNull(preRelease, "PreRelease");
        return this;
    }
    
    public VersionBuilder buildMetadata(String metadata) {
        return buildMetadata(BuildMetadata.parse(metadata));
    }
    
    public VersionBuilder buildMetadata(BuildMetadata metadata) {
        this.metadata = checkNotNull(metadata, "BuildMetadata");
        return this;
    }
    
    public Version build() {
        return new ConcreteVersion(major, minor, patch, preRelease, metadata);
    }
    
}
