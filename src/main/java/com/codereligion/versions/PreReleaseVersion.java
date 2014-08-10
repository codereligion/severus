package com.codereligion.versions;

import com.google.common.base.Joiner;
import com.google.common.base.Objects;
import com.google.common.collect.ImmutableList;

import javax.annotation.Nonnull;
import javax.annotation.concurrent.Immutable;
import java.util.Iterator;

import static com.google.common.collect.Iterables.elementsEqual;

@Immutable
public final class PreReleaseVersion implements Tuple<Identifier>, Comparable<PreReleaseVersion> {
    
    private final ImmutableList<Identifier> identifiers;

    PreReleaseVersion(ImmutableList<Identifier> identifiers) {
        this.identifiers = identifiers;
    }

    public static PreReleaseVersion parse(String version) {
        return new PreReleaseVersionParser().parse(version);
    }

    @Override
    public Iterator<Identifier> iterator() {
        return identifiers.iterator();
    }

    @Override
    public int compareTo(@Nonnull PreReleaseVersion o) {
        // TODO change to list
        return toString().compareTo(o.toString());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(identifiers);
    }

    @Override
    public boolean equals(Object that) {
        if (this == that) {
            return true;
        } else if (that instanceof PreReleaseVersion) {
            return elementsEqual(identifiers, (PreReleaseVersion) that);
        } else {
            return false;
        }
    }

    @Override
    public String toString() {
        return Joiner.on('.').join(identifiers);
    }
    
}
