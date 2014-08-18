package com.codereligion.severus;

import com.google.common.collect.ComparisonChain;
import com.google.common.collect.Ordering;

import javax.annotation.concurrent.Immutable;
import java.util.Comparator;

@Immutable
enum PreReleaseVersionPrecedence implements Comparator<PreReleaseVersion> {

    LEXICOGRAPHICAL;
    
    private final Ordering<Iterable<Identifier<?>>> lexicographical = 
            Ordering.from(IdentifierPrecedence.NATURAL).lexicographical();

    @Override
    public int compare(PreReleaseVersion left, PreReleaseVersion right) {
        return ComparisonChain.start().
                compareFalseFirst(left.isEmpty(), right.isEmpty()).
                compare(left, right, lexicographical).
                result();
    }

}
