package com.codereligion.versions;

import javax.annotation.concurrent.Immutable;

@Immutable
public interface Tuple<I extends Identifier> extends Iterable<I> {

    boolean isEmpty();

    @Override
    String toString();

}
