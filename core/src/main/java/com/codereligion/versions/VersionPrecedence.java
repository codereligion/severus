package com.codereligion.versions;

import com.google.common.collect.ComparisonChain;
import com.google.common.collect.Ordering;

import javax.annotation.concurrent.Immutable;

// TODO singleton!
@Immutable
final class VersionPrecedence extends Ordering<Version> {

    private final IdentifierPrecedence precedence = new IdentifierPrecedence();
    private final PreReleaseVersionPrecedence comparator = new PreReleaseVersionPrecedence();

    @Override
    public int compare(Version left, Version right) {
        return ComparisonChain.start().
                compare(left.getMajor(), right.getMajor(), precedence).
                compare(left.getMinor(), right.getMinor(), precedence).
                compare(left.getPatch(), right.getPatch(), precedence).
                compare(left.getPreRelease(), right.getPreRelease(), comparator).
                result();
    }

    private static final class IdentifierPrecedence extends Ordering<Identifier<?>> {

        @Override
        public int compare(Identifier<?> left, Identifier<?> right) {
            return ComparisonChain.start().
                    compareFalseFirst(left instanceof Name, right instanceof Name).
                    compare(left.getValue(), right.getValue()).
                    result();
        }

    }

    private static final class PreReleaseVersionPrecedence extends Ordering<PreReleaseVersion> {

        private final IdentifierPrecedence precedence = new IdentifierPrecedence();

        @Override
        public int compare(PreReleaseVersion left, PreReleaseVersion right) {

            return ComparisonChain.start().
                    compare(left.isEmpty(), right.isEmpty()).
                    compare(left, right, precedence.lexicographical()).
                    result();
        }
    
    }
    
}
