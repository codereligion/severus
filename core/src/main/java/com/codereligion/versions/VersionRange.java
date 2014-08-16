package com.codereligion.versions;

import com.google.common.base.Converter;
import com.google.common.collect.Range;

public final class VersionRange {
    
    private VersionRange() {
        // static factory
    }
    
    public static Range<Version> valueOf(String range) throws VersionFormatException {
        return converter().convert(range);
    }
    
    public static Range<Version> valueOf(String range, VersionPrecedence precedence) throws VersionFormatException {
        return converter(precedence).convert(range);
    }

    public static Converter<String, Range<Version>> converter() {
        return converter(VersionPrecedence.NATURAL);
    }

    public static Converter<String, Range<Version>> converter(VersionPrecedence precedence) {
        return new VersionRangeConverter(precedence);
    }
    
}
