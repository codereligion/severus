package com.codereligion.severus;

import com.google.common.collect.ComparisonChain;
import com.google.common.collect.Ordering;

import javax.annotation.Nullable;
import javax.annotation.concurrent.Immutable;
import java.util.Comparator;
import java.util.Objects;

@Immutable
public enum VersionPrecedence implements Comparator<Version> {

    NATURAL {
        
        @Override
        protected int hash(Version version) {
            return Objects.hash(version.getMajor(), version.getMinor(), version.getPatch(),
                    version.getPreRelease());
        }

        @Override
        public int compare(Version left, Version right) {
            return ComparisonChain.start().
                    compare(left.getMajor(), right.getMajor(), IdentifierPrecedence.NATURAL).
                    compare(left.getMinor(), right.getMinor(), IdentifierPrecedence.NATURAL).
                    compare(left.getPatch(), right.getPatch(), IdentifierPrecedence.NATURAL).
                    compare(left.getPreRelease(), right.getPreRelease(), PreReleaseVersionPrecedence.LEXICOGRAPHICAL).
                    result();
        }

    },

    BUILD {
        
        @Override
        public int compare(Version left, Version right) {
            return ComparisonChain.start().
                    compare(left, right, NATURAL).
                    compare(left.getBuild(), right.getBuild(), BuildMetadataPrecedence.LEXICOGRAPHICAL).
                    result();
        }

        @Override
        protected int hash(Version version) {
            return Objects.hash(version.getMajor(), version.getMinor(), version.getPatch(),
                    version.getPreRelease(), version.getBuild());
        }

    };

    public final boolean equals(Version left, Version right) {
        return compare(left, right) == 0;
    }

    abstract int hash(Version version);

}
