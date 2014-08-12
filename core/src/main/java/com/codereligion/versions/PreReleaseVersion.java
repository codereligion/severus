package com.codereligion.versions;

import com.google.common.base.Function;
import com.google.common.base.Joiner;
import com.google.common.base.Splitter;
import com.google.common.collect.FluentIterable;
import com.google.common.collect.ImmutableList;

import javax.annotation.concurrent.Immutable;
import java.util.Iterator;
import java.util.Objects;

import static com.google.common.base.Preconditions.checkNotNull;
import static com.google.common.collect.FluentIterable.from;
import static com.google.common.collect.Iterables.elementsEqual;

@Immutable
public final class PreReleaseVersion implements Tuple<Identifier<?>> {
    
    private static final PreReleaseVersion EMPTY = new PreReleaseVersion();
    
    private final ImmutableList<Identifier<?>> identifiers;
    
    private PreReleaseVersion() {
        this.identifiers = ImmutableList.of();
    }

    private PreReleaseVersion(final ImmutableList<Identifier<?>> identifiers) {
        this.identifiers = identifiers;
    }

    @Override
    public Iterator<Identifier<?>> iterator() {
        return identifiers.iterator();
    }

    @Override
    public boolean isEmpty() {
        return identifiers.isEmpty();
    }

    @Override
    public int hashCode() {
        return Objects.hash(identifiers);
    }

    @Override
    public boolean equals(Object that) {
        if (this == that) {
            return true;
        } else if (that instanceof PreReleaseVersion) {
            return elementsEqual(this, (PreReleaseVersion) that);
        } else {
            return false;
        }
    }

    @Override
    public String toString() {
        return Joiner.on('.').join(identifiers);
    }

    public static PreReleaseVersion empty() {
        return EMPTY;
    }

    public static PreReleaseVersion parse(final String version) {
        checkNotNull(version, "Version");
        
        if (version.isEmpty()) {
            return empty();
        }

        return valueOf(Splitter.on('.').split(version));
    }
    
    public static PreReleaseVersion valueOf(final Iterable<String> values) {
        checkNotNull(values, "Values");
        final FluentIterable<Identifier<?>> identifiers = from(values).transform(toIdentifier());
        return new PreReleaseVersion(identifiers.toList());
    }

    private static Function<String, Identifier<?>> toIdentifier() {
        return new Function<String, Identifier<?>>() {
            @Override
            public Identifier apply(final String value) {
                return Identifiers.parse(value);
            }
        };
    }
    
}
