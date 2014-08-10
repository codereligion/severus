package com.codereligion.versions;

import com.google.common.base.Function;
import com.google.common.base.Splitter;
import com.google.common.collect.ImmutableList;

import static com.google.common.collect.FluentIterable.from;

final class BuildMetadataParser implements Parser<BuildMetadata> {

    @Override
    public BuildMetadata parse(String value) {
        if (value.isEmpty()) {
            return new BuildMetadata(ImmutableList.<Name>of());
        }
        
        final ImmutableList<Name> identifiers = 
                from(Splitter.on('.').trimResults().split(value)).transform(toName()).toList();

        return new BuildMetadata(identifiers);
    }
    
    private Function<String, Name> toName() {
        return new Function<String, Name>() {
            @Override
            public Name apply(final String s) {
                return new Name(s);
            }
        };
    }

}
