package com.codereligion.versions;

import javax.annotation.concurrent.Immutable;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static com.google.common.base.Preconditions.checkArgument;
import static com.google.common.base.Strings.nullToEmpty;
import static java.util.regex.Pattern.compile;

@Immutable
final class VersionParser implements Parser<Version> {
    
    private final Pattern pattern = compile("^(0|[1-9][0-9]*)\\.(0|[1-9][0-9]*)\\.(0|[1-9][0-9]*)(?:-([.0-9A-Za-z-]+))?(?:\\+([.0-9A-Za-z-]+))?");
    
    @Override
    public Version parse(String value) {
        final Matcher matcher = pattern.matcher(value);
        checkArgument(matcher.matches(), "[%s] doesn't match [%s]", value, pattern);

        final VersionNumber major = Versions.Numbers.parse(matcher.group(1));
        final VersionNumber minor = Versions.Numbers.parse(matcher.group(2));
        final VersionNumber patch = Versions.Numbers.parse(matcher.group(3));
        final PreReleaseVersion preRelease = Versions.PreRelease.parse(nullToEmpty(matcher.group(4)));
        final BuildMetadata buildMetadata = Versions.Metadata.parse(nullToEmpty(matcher.group(5)));
        
        return new ConcreteVersion(major, minor, patch, preRelease, buildMetadata);
    }

}
