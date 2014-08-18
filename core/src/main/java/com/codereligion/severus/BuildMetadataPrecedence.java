package com.codereligion.severus;

import com.google.common.collect.ComparisonChain;
import com.google.common.collect.Ordering;

import javax.annotation.concurrent.Immutable;
import java.util.Comparator;

@Immutable
enum BuildMetadataPrecedence implements Comparator<BuildMetadata> {

    LEXICOGRAPHICAL;
    
    private final Ordering<Iterable<Name>> lexicographical = 
            Ordering.from(IdentifierPrecedence.NATURAL).lexicographical();

    @Override
    public int compare(BuildMetadata left, BuildMetadata right) {
        return ComparisonChain.start().
                compareFalseFirst(left.isEmpty(), right.isEmpty()).
                compare(left, right, lexicographical).
                result();
    }
    
}
