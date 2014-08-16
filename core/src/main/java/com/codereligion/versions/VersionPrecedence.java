package com.codereligion.versions;

import com.google.common.base.Equivalence;
import com.google.common.collect.ComparisonChain;

import javax.annotation.concurrent.Immutable;
import java.io.Serializable;
import java.util.Comparator;
import java.util.Objects;

@Immutable
public abstract class VersionPrecedence extends Equivalence<Version> implements Comparator<Version>, Serializable {

    private VersionPrecedence() {
        // not to be used outside
    }

    public static final VersionPrecedence NATURAL = new VersionPrecedence() {

        @Override
        public int compare(Version left, Version right) {
            return ComparisonChain.start().
                    compare(left.getMajor(), right.getMajor(), IdentifierPrecedence.NATURAL).
                    compare(left.getMinor(), right.getMinor(), IdentifierPrecedence.NATURAL).
                    compare(left.getPatch(), right.getPatch(), IdentifierPrecedence.NATURAL).
                    compare(left.getPreRelease(), right.getPreRelease(), PreReleaseVersionPrecedence.LEXICOGRAPHICAL).
                    result();
        }

        @Override
        protected int doHash(Version version) {
            return Objects.hash(version.getMajor(), version.getMinor(), version.getPatch(),
                    version.getPreRelease());
        }

        @SuppressWarnings({"SameReturnValue", "unused"})
        private Object readResolve() {
            return NATURAL;
        }

        @Override
        public String toString() {
            return "NATURAL";
        }

    };

    public static final VersionPrecedence BUILD = new VersionPrecedence() {

        @Override
        public int compare(Version left, Version right) {
            return ComparisonChain.start().
                    compare(left, right, NATURAL).
                    compare(left.getBuild(), right.getBuild(), BuildMetadataPrecedence.LEXICOGRAPHICAL).
                    result();
        }

        @Override
        protected int doHash(Version version) {
            return Objects.hash(version.getMajor(), version.getMinor(), version.getPatch(),
                    version.getPreRelease(), version.getBuild());
        }
        
        @SuppressWarnings({"SameReturnValue", "unused"})
        private Object readResolve() {
            return BUILD;
        }

        @Override
        public String toString() {
            return "BUILD";
        }

    };

    @Override
    protected boolean doEquivalent(Version left, Version right) {
        return compare(left, right) == 0;
    }

}
