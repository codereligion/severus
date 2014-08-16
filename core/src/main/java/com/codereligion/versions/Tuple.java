package com.codereligion.versions;

import javax.annotation.concurrent.Immutable;
import java.io.Serializable;

@Immutable
public interface Tuple<I extends Identifier> extends Iterable<I>, Serializable {

    boolean isEmpty();

    @Override
    String toString();

}
