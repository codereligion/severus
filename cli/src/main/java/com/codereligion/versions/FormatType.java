package com.codereligion.versions;

import com.google.common.base.Joiner;
import net.sourceforge.argparse4j.inf.Argument;
import net.sourceforge.argparse4j.inf.ArgumentChoice;
import net.sourceforge.argparse4j.inf.ArgumentParser;
import net.sourceforge.argparse4j.inf.ArgumentParserException;
import net.sourceforge.argparse4j.inf.ArgumentType;

import javax.annotation.concurrent.Immutable;
import java.util.Locale;

enum FormatType implements ArgumentType<Format>, ArgumentChoice {
    
    INSTANCE;

    @Override
    public Format convert(ArgumentParser parser, Argument arg, String value) throws ArgumentParserException {
        try {
            return Format.valueOf(value.toUpperCase(Locale.ENGLISH));
        } catch (IllegalArgumentException e) {
            return null;
        }
    }

    @Override
    public boolean contains(Object format) {
        return format != null;
    }

    @Override
    public String textualFormat() {
        return Joiner.on(", ").join(Format.choices());
    }
    
}
