package com.codereligion.versions;

import com.google.common.collect.ComparisonChain;
import com.google.common.collect.Ordering;

import static java.lang.Integer.parseInt;

public final class Versions {
    
    private Versions() {
        // static factory
    }
    
    public static Ordering<Version> naturalOrdering() {
        return new Ordering<Version>() {
            @Override
            public int compare(Version left, Version right) {
                return ComparisonChain.start().
                        compare(left.getMajorVersion(), right.getMajorVersion()).
                        compare(left.getMinorVersion(), right.getMinorVersion()).
                        compare(left.getPatchVersion(), right.getPatchVersion()).
                        compare(left.getPreReleaseVersion(), right.getPreReleaseVersion(), natural().nullsLast()).
                        result();
            }
        };
    }

    public static final class Numbers {
        
        private Numbers() {
            // static factory
        }
        
        public static VersionNumber parse(final String number) {
            final int value = parseInt(number);
            return new DefaultVersionNumber(value);
        }

    }
    
    public static final class PreRelease {
        
        private PreRelease() {
            // static factory
        }
        
        public static PreReleaseVersion parse(String version) {
            return new PreReleaseVersionParser().parse(version);
        }
        
    }
    
    public static final class Metadata {
        
        private Metadata() {
            // static factory
        }
        
        public static BuildMetadata parse(String metadata) {
            return new BuildMetadataParser().parse(metadata);
        }
        
    }

}
