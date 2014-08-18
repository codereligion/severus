package com.codereligion.severus;

import javax.annotation.Nullable;
import javax.annotation.concurrent.Immutable;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static com.google.common.base.Objects.firstNonNull;
import static com.google.common.base.Strings.nullToEmpty;
import static java.lang.String.format;
import static java.util.regex.Pattern.compile;

@Immutable
final class PatternVersionParser implements Parser<Version> {

    private static final Pattern VERSION_NUMBER = compile("0|[1-9][0-9]*");
    private static final Pattern PATCH = compile("(" + VERSION_NUMBER + ")");
    private static final Pattern MINOR = compile("(" + VERSION_NUMBER + ")");
    private static final Pattern MAJOR = compile("(" + VERSION_NUMBER + ")");
    private static final Pattern NAME = compile("[0-9A-Za-z-]+");
    private static final Pattern BUILD = compile("(?:\\+(" + NAME + "(?:\\." + NAME + ")*))?");
    private static final Pattern IDENTIFIER = compile("(?:(?:" + VERSION_NUMBER + ")|(?:[1-9A-Za-z-]" + NAME + "))");
    private static final Pattern PRE_RELEASE = compile("(?:-(" + IDENTIFIER + "(?:\\." + IDENTIFIER + ")*))?");
    
    private static final Pattern VERSION = compile(MAJOR + "\\." + MINOR + "\\." + PATCH + PRE_RELEASE + BUILD);
    
    // TODO fix visibility
    static final Pattern SHORT = compile(MAJOR + "(?:\\." + MINOR + ")?" + "(?:\\." + PATCH + ")?" + PRE_RELEASE + BUILD);
    
    @Override
    public Version parse(String version, VersionPrecedence precedence) {
        return parse(version, precedence, ParseMode.NORMAL);
    }

    @Override
    public Version parse(String version, VersionPrecedence precedence, ParseMode mode) {
        switch (mode) {
            case NORMAL:
                return parse(version, precedence, VERSION, null);
            case SHORT:
                return parse(version, precedence, SHORT, "0");
            default:
                throw new UnsupportedOperationException("Unknown parse mode:" + mode);
        }
    }

    private Version parse(String version, VersionPrecedence precedence, Pattern pattern, @Nullable String defaultValue) {
        final Matcher matcher = pattern.matcher(version);

        if (!matcher.matches()) {
            throw new VersionFormatException(version + " is not a valid version");
        }

        return new ConcreteVersion(
                VersionNumber.valueOf(firstNonNull(matcher.group(1), defaultValue)),
                VersionNumber.valueOf(firstNonNull(matcher.group(2), defaultValue)),
                VersionNumber.valueOf(firstNonNull(matcher.group(3), defaultValue)),
                PreReleaseVersion.valueOf(nullToEmpty(matcher.group(4))),
                BuildMetadata.valueOf(nullToEmpty(matcher.group(5))),
                precedence
        );
    }

}
