package com.codereligion.versions;

import net.sourceforge.argparse4j.inf.Argument;
import net.sourceforge.argparse4j.inf.ArgumentParser;
import net.sourceforge.argparse4j.inf.ArgumentParserException;
import net.sourceforge.argparse4j.inf.ArgumentType;

final class VersionType implements ArgumentType<Version> {

    @Override
    public Version convert(ArgumentParser parser, Argument arg, String value) throws ArgumentParserException {
        return Version.valueOf(value);
    }
    
}
