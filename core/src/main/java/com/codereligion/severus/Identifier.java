package com.codereligion.severus;

import javax.annotation.concurrent.Immutable;
import java.io.Serializable;

@Immutable
public interface Identifier<T> extends Serializable {
    
    Comparable<T> getValue();
    
    @Override
    boolean equals(Object object);

    @Override
    int hashCode();
    
    @Override
    String toString();
    
}
