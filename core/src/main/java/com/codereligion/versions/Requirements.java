package com.codereligion.versions;

import com.google.common.base.CharMatcher;
import com.google.common.base.Joiner;
import com.google.common.base.Throwables;
import com.google.common.cache.CacheBuilder;
import com.google.common.cache.CacheLoader;
import com.google.common.cache.LoadingCache;
import com.google.common.collect.ImmutableList;

import java.math.BigInteger;
import java.util.concurrent.ExecutionException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static com.google.common.base.Preconditions.checkArgument;
import static java.lang.String.format;
import static java.util.regex.Pattern.compile;

final class Requirements {

    private static final Pattern VERSION_NUMBER = compile("(0|[1-9][0-9]*)");
    private static final Pattern MAJOR = VERSION_NUMBER;
    private static final Pattern MINOR = VERSION_NUMBER;
    private static final Pattern PATCH = VERSION_NUMBER;
    private static final Pattern DOT = compile("\\.");
    private static final Pattern ALPHA = compile("[0-9A-Za-z-]+");
    private static final Pattern ALPHA_NUMERIC = compile("(?:(?:0|[1-9][0-9]*)|(?:[1-9A-Za-z-]" + ALPHA + "))");
    private static final Pattern PRE_RELEASE = compile("(?:-(" + ALPHA_NUMERIC + "(?:\\." + ALPHA_NUMERIC + ")*))?");
    private static final Pattern BUILD = compile("(?:\\+(" + ALPHA + "(?:\\." + ALPHA + ")*))?");

    private static final LoadingCache<ImmutableList<Pattern>, Pattern> PATTERN_CACHE =
            CacheBuilder.newBuilder().build(new CacheLoader<ImmutableList<Pattern>, Pattern>() {
                @Override
                public Pattern load(ImmutableList<Pattern> pattern) throws Exception {
                    return Pattern.compile(Joiner.on("").join(pattern));
                }
            });

    private static final CharMatcher ZERO = CharMatcher.is('0');

    private Requirements() {
        // utility class
    }

    private static Pattern combine(ImmutableList<Pattern> patterns) {
        try {
            return PATTERN_CACHE.get(patterns);
        } catch (ExecutionException e) {
            throw Throwables.propagate(e.getCause());
        }
    }

    static Matcher match(String version) {
        final ImmutableList<Pattern> patterns = ImmutableList.of(MAJOR, DOT, MINOR, DOT, PATCH, PRE_RELEASE, BUILD);

        final Pattern pattern = combine(patterns);

        final Matcher matcher = pattern.matcher(version);

        if (!matcher.matches()) {
            // TODO improve message
            throw new VersionFormatException(format("%s doesn't match %s", version, pattern));
        }

        return matcher;
    }

    static void checkNonNegative(BigInteger value) {
        checkArgument(value.compareTo(BigInteger.ZERO) >= 0,
                "Value must not be negative, but was [%s]", value);
    }

    static void checkNoLeadingZero(String value) {
        checkArgument("0".equals(value) || !ZERO.matches(value.charAt(0)),
                "Value must not start with a zero, but was [%s]", value);
    }

    static void checkNotEmpty(String value) {
        checkArgument(!value.isEmpty(),
                "Value must not be empty, but was [%s]", value);
    }

}
