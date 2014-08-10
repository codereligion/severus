package com.codereligion.versions;

import javax.annotation.concurrent.Immutable;

@Immutable
public interface Identifier {

    @Override
    boolean equals(Object object);

    @Override
    int hashCode();
    
    @Override
    String toString();
    
}
