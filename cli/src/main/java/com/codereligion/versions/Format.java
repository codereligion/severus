package com.codereligion.versions;

import com.google.common.base.Function;

import java.util.Collection;
import java.util.Locale;

import static com.google.common.collect.FluentIterable.from;
import static java.util.Arrays.asList;

enum Format {

    TEXT, SCRIPT, JSON, XML;
    
    public static Collection<String> choices() {
        return from(asList(values())).transform(toLowerCase()).toList();
    }

    private static Function<Format, String> toLowerCase() {
        return new Function<Format, String>() {
            @Override
            public String apply(Format input) {
                return input.name().toLowerCase(Locale.ENGLISH);
            }
        };
    }

}
