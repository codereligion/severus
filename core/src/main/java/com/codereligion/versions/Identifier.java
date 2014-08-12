package com.codereligion.versions;

import javax.annotation.concurrent.Immutable;

@Immutable
public interface Identifier<T> {
    
    Comparable<T> getValue();
    
    @Override
    boolean equals(Object object);

    @Override
    int hashCode();
    
    @Override
    String toString();
    
}
