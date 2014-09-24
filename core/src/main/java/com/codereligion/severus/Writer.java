package com.codereligion.severus;

import javax.annotation.concurrent.Immutable;

@Immutable
interface Writer<T> {

    String write(T value);

}
