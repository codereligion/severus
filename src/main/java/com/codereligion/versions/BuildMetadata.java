package com.codereligion.versions;

import com.google.common.base.Joiner;
import com.google.common.base.Objects;
import com.google.common.collect.ImmutableList;

import javax.annotation.concurrent.Immutable;
import java.util.Iterator;

import static com.google.common.collect.Iterables.elementsEqual;

@Immutable
public final class BuildMetadata implements Tuple<Name> {
    
    private final ImmutableList<Name> identifiers;

    public BuildMetadata(ImmutableList<Name> identifiers) {
        this.identifiers = identifiers;
    }

    @Override
    public Iterator<Name> iterator() {
        return identifiers.iterator();
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(identifiers);
    }

    @Override
    public boolean equals(Object that) {
        if (this == that) {
            return true;
        } else if (that instanceof BuildMetadata) {
            return elementsEqual(identifiers, (BuildMetadata) that);
        } else {
            return false;
        }
    }

    @Override
    public String toString() {
        return Joiner.on('.').join(identifiers);
    }
    
}
