package com.codereligion.versions;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static com.google.common.base.Preconditions.checkArgument;
import static com.google.common.base.Preconditions.checkNotNull;
import static com.google.common.base.Strings.nullToEmpty;
import static java.lang.String.format;
import static java.util.regex.Pattern.compile;

public final class VersionBuilder {

    private static final Pattern PATTERN = compile(
            "^(0|[1-9][0-9]*)\\.(0|[1-9][0-9]*)\\.(0|[1-9][0-9]*)(?:-([.0-9A-Za-z-]+))?(?:\\+([.0-9A-Za-z-]+))?");
    
    private VersionNumber major = VersionNumber.valueOf(0);
    private VersionNumber minor = VersionNumber.valueOf(0);
    private VersionNumber patch = VersionNumber.valueOf(0);
    private PreReleaseVersion preRelease = PreReleaseVersion.empty();
    private BuildMetadata build = BuildMetadata.empty();
    private VersionPrecedence precedence = VersionPrecedence.NATURAL;
    
    VersionBuilder() {
        // should only be accessible from static factory method
    }

    public VersionBuilder parse(String version) throws VersionFormatException {
        checkNotNull(version, "Version");
        
        final Matcher matcher = PATTERN.matcher(version);
        
        if (!matcher.matches()) {
            throw new VersionFormatException(format("%s doesn't match %s", version, PATTERN));
        }

        major(matcher.group(1));
        minor(matcher.group(2));
        patch(matcher.group(3));
        preRelease(nullToEmpty(matcher.group(4)));
        build(nullToEmpty(matcher.group(5)));
        
        return this;
    }

    public VersionBuilder major(int major) {
        return major(VersionNumber.valueOf(major));
    }
    
    public VersionBuilder major(String major) {
        return major(VersionNumber.valueOf(major));
    }
    
    public VersionBuilder major(VersionNumber major) {
        this.major = checkNotNull(major, "Major");
        return this;
    }
    
    public VersionBuilder minor(int minor) {
        return minor(VersionNumber.valueOf(minor));
    }
    
    public VersionBuilder minor(String minor) {
        return minor(VersionNumber.valueOf(minor));
    }
    
    public VersionBuilder minor(VersionNumber minor) {
        this.minor = checkNotNull(minor, "Minor");
        return this;
    }
    
    public VersionBuilder patch(int patch) {
        return patch(VersionNumber.valueOf(patch));
    }
    
    public VersionBuilder patch(String patch) {
        return patch(VersionNumber.valueOf(patch));
    }
    
    public VersionBuilder patch(VersionNumber patch) {
        this.patch = checkNotNull(patch, "Patch");
        return this;
    }
    
    public VersionBuilder preRelease(String version) {
        return preRelease(PreReleaseVersion.valueOf(version));
    }
    
    public VersionBuilder preRelease(PreReleaseVersion preRelease) {
        this.preRelease = checkNotNull(preRelease, "PreRelease");
        return this;
    }
    
    public VersionBuilder build(String build) {
        return build(BuildMetadata.valueOf(build));
    }
    
    public VersionBuilder build(BuildMetadata build) {
        this.build = checkNotNull(build, "BuildMetadata");
        return this;
    }
    
    public VersionBuilder precendence(VersionPrecedence precedence) {
        this.precedence = precedence;
        return this;
    }
    
    public Version create() {
        return new ConcreteVersion(major, minor, patch, preRelease, build, precedence);
    }
    
}
