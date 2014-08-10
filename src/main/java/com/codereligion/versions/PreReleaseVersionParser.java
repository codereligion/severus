package com.codereligion.versions;

import com.google.common.base.CharMatcher;
import com.google.common.base.Function;
import com.google.common.base.Splitter;
import com.google.common.collect.ImmutableList;

import static com.google.common.collect.FluentIterable.from;

final class PreReleaseVersionParser implements Parser<PreReleaseVersion> {

    @Override
    public PreReleaseVersion parse(String value) {
        if (value.isEmpty()) {
            return new PreReleaseVersion(ImmutableList.<Identifier>of());
        }

        final ImmutableList<Identifier> identifiers = 
                from(Splitter.on('.').trimResults().split(value)).transform(toIdentifier()).toList();

        return new PreReleaseVersion(identifiers);
    }
    
    private Function<String, Identifier> toIdentifier() {
        return new Function<String, Identifier>() {
            @Override
            public Identifier apply(final String s) {
                if (CharMatcher.inRange('0', '9').matchesAllOf(s)) {
                    return Versions.Numbers.parse(s);
                } else {
                    return new DefaultName(s);
                }
            }
        };
    }

}
