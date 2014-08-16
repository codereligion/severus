package com.codereligion.versions;

import com.google.common.base.Joiner;
import net.sourceforge.argparse4j.inf.Argument;
import net.sourceforge.argparse4j.inf.ArgumentChoice;
import net.sourceforge.argparse4j.inf.ArgumentParser;
import net.sourceforge.argparse4j.inf.ArgumentParserException;
import net.sourceforge.argparse4j.inf.ArgumentType;

import javax.annotation.Nullable;

enum PrecedenceType implements ArgumentType<VersionPrecedence>, ArgumentChoice {
    
    INSTANCE;

    @Override
    public VersionPrecedence convert(ArgumentParser parser, Argument arg, String value) throws ArgumentParserException {
        switch (value) {
            case "natural":
                return VersionPrecedence.NATURAL;
            case "build":
                return VersionPrecedence.BUILD;
            default:
                return null;
        }
    }
    
    @Override
    public boolean contains(@Nullable Object precedence) {
        return precedence != null;
    }

    @Override
    public String textualFormat() {
        return Joiner.on(", ").join("natural", "build");
    }
    
}
