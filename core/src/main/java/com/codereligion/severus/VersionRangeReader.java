package com.codereligion.severus;

import com.google.common.base.Optional;
import com.google.common.collect.BoundType;
import com.google.common.collect.ImmutableRangeSet;
import com.google.common.collect.Range;

import javax.annotation.concurrent.Immutable;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static com.codereligion.severus.VersionReader.SHORT;
import static java.util.regex.Pattern.compile;

@Immutable
final class VersionRangeReader implements Reader<VersionRange> {
    
    private final Reader<Version> reader = new VersionReader();
    
    private static final Pattern SINGLE = compile("\\[(.+)\\]");
    private static final Pattern DOUBLE = compile("([\\[\\(])(.*?),(.*?)([\\)\\]])");
    private static final Pattern ANY_RANGE = compile("((?:" + SHORT + ")|(?:" + SINGLE + ")|(?:" + DOUBLE + "))");
    private static final Pattern RANGE_SET = compile(ANY_RANGE + "(?:," + ANY_RANGE + ")*");

    @Override
    public VersionRange read(String range, VersionPrecedence precedence) {
        return read(range, precedence, ReadMode.NORMAL);
    }

    @Override
    public VersionRange read(String range, VersionPrecedence precedence, ReadMode mode) {
        if (range.isEmpty()) {
            return VersionRange.empty(); 
        } else if (RANGE_SET.matcher(range).matches()) {
            final Matcher matcher = ANY_RANGE.matcher(range);

            final ImmutableRangeSet.Builder<Version> builder = ImmutableRangeSet.builder();

            while (matcher.find()) {
                try {
                    final Range<Version> part = parseRange(matcher.group(), precedence);
                    
                    if (part.isEmpty()) {
                        continue;
                    }
                    
                    builder.add(part);
                } catch (IllegalArgumentException e) {
                    throw new VersionFormatException(e);
                }
            }

            return new VersionRange(builder.build());
        } else {
            return new VersionRange(ImmutableRangeSet.of(parseRange(range, precedence)));
        }
    }

    private Range<Version> parseRange(String range, VersionPrecedence precedence) {
        // TODO add tests for pre-release
        // TODO add support for precedence + tests for builds

        final Matcher matcher = DOUBLE.matcher(range);
        
        if (matcher.matches()) {
            final BoundType lowerType = parse(matcher.group(1).charAt(0));
            final Optional<Version> lower = parseVersion(matcher.group(2), precedence);
            final Optional<Version> upper = parseVersion(matcher.group(3), precedence);
            final BoundType upperType = parse(matcher.group(4).charAt(0));

            return build(lowerType, lower, upper, upperType);
        } else {
            return parseSingle(range, precedence);
        }
    }

    private Range<Version> parseSingle(String range, VersionPrecedence precedence) {
        final Matcher matcher = SINGLE.matcher(range);

        if (matcher.matches()) {
            return Range.singleton(reader.read(matcher.group(1), precedence, ReadMode.SHORT));
        }

        // TODO verify first
        return Range.singleton(reader.read(range, precedence, ReadMode.SHORT));
    }

    private Range<Version> build(BoundType lowerType, Optional<Version> lower, Optional<Version> upper, BoundType upperType) {
        if (lower.isPresent() && upper.isPresent()) {
            try {
                return Range.range(lower.get(), lowerType, upper.get(), upperType);
            } catch (IllegalArgumentException e) {
                throw new VersionFormatException(e);
            }
        } else if (lower.isPresent()) {
            if (upperType == BoundType.CLOSED) {
                throw new VersionFormatException("Unbound upper range can't be closed");
            }
            
            return Range.downTo(lower.get(), lowerType);
        } else if (upper.isPresent()) {
            if (lowerType == BoundType.CLOSED) {
                throw new VersionFormatException("Unbound lower range can't be closed");
            }
            
            return Range.upTo(upper.get(), upperType);
        } else {
            return Range.all();
        }
    }

    private Optional<Version> parseVersion(String version, VersionPrecedence precedence) {
        if (version.isEmpty()) {
            return Optional.absent();
        } else {
            return Optional.of(reader.read(version, precedence, ReadMode.SHORT));
        }
    }

    private BoundType parse(char c) {
        switch (c) {
            case '(':
            case ')':
                return BoundType.OPEN;
            case '[':
            case ']':
                return BoundType.CLOSED;
            default:
                throw new UnsupportedOperationException("Unsupported bound type: " + c);
        }
    }
    
}
