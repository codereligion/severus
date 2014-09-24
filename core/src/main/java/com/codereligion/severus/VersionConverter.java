package com.codereligion.severus;

import com.google.common.base.Converter;

import javax.annotation.concurrent.Immutable;

@Immutable
final class VersionConverter extends Converter<String, Version> {
    
    private final Reader<Version> reader = new VersionReader();
    private final Writer<Version> writer = new VersionWriter();
    
    private final VersionPrecedence precedence;

    VersionConverter(VersionPrecedence precedence) {
        this.precedence = precedence;
    }

    @Override
    protected Version doForward(String version) {
        return reader.read(version, precedence);
    }

    @Override
    protected String doBackward(Version version) {
        return writer.write(version);
    }

}
