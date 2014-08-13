package com.codereligion.versions;

import net.sourceforge.argparse4j.inf.Argument;
import net.sourceforge.argparse4j.inf.ArgumentParser;
import net.sourceforge.argparse4j.inf.ArgumentParserException;
import net.sourceforge.argparse4j.inf.ArgumentType;

import java.util.Locale;

final class FormatType implements ArgumentType<Format> {

    @Override
    public Format convert(ArgumentParser parser, Argument arg, String value) throws ArgumentParserException {
        return Format.valueOf(value.toUpperCase(Locale.ENGLISH));
    }
    
}
