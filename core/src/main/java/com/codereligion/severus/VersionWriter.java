package com.codereligion.severus;

import com.google.common.base.Joiner;

import javax.annotation.concurrent.Immutable;

@Immutable
final class VersionWriter implements Writer<Version> {

    private final Joiner dot = Joiner.on('.');

    @Override
    public String write(Version version) {
        final StringBuilder result = new StringBuilder();
        
        dot.appendTo(result, version.getMajor(), version.getMinor(), version.getPatch());
        appendIfPresent(result, "-", version.getPreRelease());
        appendIfPresent(result, "+", version.getBuild());
        
        return result.toString();
    }

    private void appendIfPresent(StringBuilder builder, String prefix, Tuple<?> tuple) {
        if (tuple.isEmpty()) {
            return;
        }

        builder.append(prefix).append(tuple);
    }
    
}
