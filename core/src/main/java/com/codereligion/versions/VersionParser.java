package com.codereligion.versions;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static com.google.common.base.Preconditions.checkArgument;
import static com.google.common.base.Strings.nullToEmpty;
import static java.util.regex.Pattern.compile;

final class VersionParser {
    
    private VersionParser() {
        // static factory
    }

    private static final Pattern PATTERN = compile(
            "^(0|[1-9][0-9]*)\\.(0|[1-9][0-9]*)\\.(0|[1-9][0-9]*)(?:-([.0-9A-Za-z-]+))?(?:\\+([.0-9A-Za-z-]+))?");

    static Version parse(final String version) {
        final Matcher matcher = PATTERN.matcher(version);
        checkArgument(matcher.matches(), "[%s] doesn't match [%s]", version, PATTERN);

        final VersionBuilder builder = Version.builder();
        
        builder.major(matcher.group(1));
        builder.minor(matcher.group(2));
        builder.patch(matcher.group(3));
        builder.preRelease(nullToEmpty(matcher.group(4)));
        builder.buildMetadata(nullToEmpty(matcher.group(5)));
        
        return builder.build();
    }
    
}
