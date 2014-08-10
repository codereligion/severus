package com.codereligion.versions;

import javax.annotation.concurrent.Immutable;

@Immutable
interface Parser<T> {

    T parse(final String value);

}
