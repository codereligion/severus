package com.codereligion.versions;

import javax.annotation.concurrent.Immutable;

@Immutable
interface Parser<T> {

    enum ParseMode {
        NORMAL, SHORT
    }

    T parse(String value, VersionPrecedence precedence);

    T parse(String value, VersionPrecedence precedence, ParseMode mode);
}
