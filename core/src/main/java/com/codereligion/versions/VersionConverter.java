package com.codereligion.versions;

import com.google.common.base.Converter;
import com.google.common.base.Joiner;

import javax.annotation.concurrent.Immutable;

@Immutable
final class VersionConverter extends Converter<String, Version> {

    private final Joiner dot = Joiner.on('.');
    
    private final VersionPrecedence precedence;

    VersionConverter(VersionPrecedence precedence) {
        this.precedence = precedence;
    }

    @Override
    protected Version doForward(String version) {
        return Version.builder().parse(version).precedence(precedence).create();
    }

    @Override
    protected String doBackward(Version version) {
        final StringBuilder result = new StringBuilder();
        
        dot.appendTo(result, version.getMajor(), version.getMinor(), version.getPatch());
        append(result, "-", version.getPreRelease());
        append(result, "+", version.getBuild());
        
        return result.toString();
    }

    private void append(StringBuilder builder, String prefix, Tuple<?> tuple) {
        if (tuple.isEmpty()) {
            return;
        }

        builder.append(prefix).append(tuple);
    }

}
