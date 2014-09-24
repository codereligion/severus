package com.codereligion.severus;

import com.google.common.base.Converter;

import javax.annotation.concurrent.Immutable;

@Immutable
final class VersionRangeConverter extends Converter<String, VersionRange> {
    
    private final Reader<VersionRange> reader = new VersionRangeReader();
    private final Writer<VersionRange> writer = new VersionRangeWriter();
    
    private final VersionPrecedence precedence;

    VersionRangeConverter(VersionPrecedence precedence) {
        this.precedence = precedence;
    }

    @Override
    protected VersionRange doForward(String range) {
        return reader.read(range, precedence);
    }

    @Override
    protected String doBackward(VersionRange range) {
        return writer.write(range);
    }
    
}
