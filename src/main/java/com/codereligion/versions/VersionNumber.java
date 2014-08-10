package com.codereligion.versions;

import javax.annotation.concurrent.Immutable;

@Immutable
public interface VersionNumber extends Identifier, Comparable<VersionNumber> {

    int getValue();
    
}
