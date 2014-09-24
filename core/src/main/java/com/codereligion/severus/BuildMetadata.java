package com.codereligion.severus;

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
public final class BuildMetadata implements Tuple<Name> {

    private static final BuildMetadata EMPTY = new BuildMetadata();
    
    private final ImmutableList<Name> identifiers;

    private BuildMetadata() {
        this.identifiers = ImmutableList.of();
    }
    
    private BuildMetadata(ImmutableList<Name> identifiers) {
        this.identifiers = identifiers;
    }

    @Override
    public Iterator<Name> iterator() {
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
        } else if (that instanceof BuildMetadata) {
            final BuildMetadata other = (BuildMetadata) that;
            return elementsEqual(this, other);
        } else {
            return false;
        }
    }

    @Override
    public String toString() {
        return Joiner.on('.').join(identifiers);
    }

    public static BuildMetadata empty() {
        return EMPTY;
    }

    // TODO should be in VersionReader
    public static BuildMetadata valueOf(final String build) {
        checkNotNull(build, "BuildMetadata");
        
        if (build.isEmpty()) {
            return empty();
        }

        return valueOf(Splitter.on('.').split(build));
    }
    
    public static BuildMetadata valueOf(final Iterable<String> values) {
        checkNotNull(values, "Values");
        final FluentIterable<Name> names = from(values).transform(toName());
        return new BuildMetadata(names.toList());
    }

    private static Function<String, Name> toName() {
        return new NullHostileFunction<String, Name>() {
            @Override
            public Name apply(final String value) {
                return Name.valueOf(value);
            }
        };
    }
    
}
