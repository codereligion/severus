package com.codereligion.versions;

import javax.annotation.concurrent.Immutable;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static com.google.common.base.Preconditions.checkArgument;
import static com.google.common.base.Strings.nullToEmpty;
import static java.lang.Integer.parseInt;
import static java.util.regex.Pattern.compile;

@Immutable
final class VersionParser implements Parser<Version> {
    
    private final Pattern pattern = compile("^(0|[1-9][0-9]*)\\.(0|[1-9][0-9]*)\\.(0|[1-9][0-9]*)(?:-([.0-9A-Za-z-]+))?(?:\\+([.0-9A-Za-z-]+))?");
    
    @Override
    public Version parse(String value) {
        final Matcher matcher = pattern.matcher(value);
        checkArgument(matcher.matches(), "[%s] doesn't match [%s]", value, pattern);

        final VersionNumber major = VersionNumber.parse(matcher.group(1));
        final VersionNumber minor = VersionNumber.parse(matcher.group(2));
        final VersionNumber patch = VersionNumber.parse(matcher.group(3));
        final PreReleaseVersion preRelease = PreReleaseVersion.parse(nullToEmpty(matcher.group(4)));
        final BuildMetadata buildMetadata = BuildMetadata.parse(nullToEmpty(matcher.group(5)));
        
        // TODO use builder?
        return new ConcreteVersion(major, minor, patch, preRelease, buildMetadata);
    }

}
