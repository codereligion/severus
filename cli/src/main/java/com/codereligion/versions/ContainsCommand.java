package com.codereligion.versions;

import com.google.common.collect.Range;

final class ContainsCommand implements Command {

    @Override
    public int execute(Options options) {
        final VersionRange range = options.getRange();
        final Version version = options.getVersion();
     
        return range.contains(version) ? 0 : 1;
    }
    
}
