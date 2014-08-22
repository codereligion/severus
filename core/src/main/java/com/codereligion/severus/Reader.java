package com.codereligion.severus;

import javax.annotation.concurrent.Immutable;

@Immutable
interface Reader<T> {

    enum ReadMode {
        NORMAL, SHORT
    }

    T read(String value, VersionPrecedence precedence);

    T read(String value, VersionPrecedence precedence, ReadMode mode);
}
