package com.codereligion.versions;

import com.google.common.collect.ComparisonChain;

import javax.annotation.concurrent.Immutable;
import java.util.Comparator;

@Immutable
enum IdentifierPrecedence implements Comparator<Identifier<?>> {

    NATURAL;

    @Override
    public int compare(Identifier<?> left, Identifier<?> right) {
        return ComparisonChain.start().
                compareFalseFirst(left instanceof Name, right instanceof Name).
                compare(left.getValue(), right.getValue()).
                result();
    }

}
