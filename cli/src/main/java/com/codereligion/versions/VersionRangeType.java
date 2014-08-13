package com.codereligion.versions;

import com.google.common.collect.Range;
import net.sourceforge.argparse4j.inf.Argument;
import net.sourceforge.argparse4j.inf.ArgumentParser;
import net.sourceforge.argparse4j.inf.ArgumentParserException;
import net.sourceforge.argparse4j.inf.ArgumentType;

final class VersionRangeType implements ArgumentType<Range<Version>> {

    @Override
    public Range<Version> convert(ArgumentParser parser, Argument arg, String value) throws ArgumentParserException {
        return VersionRange.valueOf(value);
    }
    
}
