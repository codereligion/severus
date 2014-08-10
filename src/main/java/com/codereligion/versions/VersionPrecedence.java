package com.codereligion.versions;

import com.google.common.collect.ComparisonChain;
import com.google.common.collect.Ordering;

final class VersionPrecedence extends Ordering<Version> {
    
    @Override
    public int compare(Version left, Version right) {
        return ComparisonChain.start().
                compare(left.getMajorVersion(), right.getMajorVersion()).
                compare(left.getMinorVersion(), right.getMinorVersion()).
                compare(left.getPatchVersion(), right.getPatchVersion()).
                compare(left.getPreReleaseVersion(), right.getPreReleaseVersion(), natural().nullsLast()).
                result();
    }
    
    // TODO build metadata aware version?
    
}
