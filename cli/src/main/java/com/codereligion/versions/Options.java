package com.codereligion.versions;

import com.google.common.collect.Range;
import net.sourceforge.argparse4j.annotation.Arg;

final class Options {
    
    @Arg
    private Command command;
    
    @Arg
    private Format format;
    
    @Arg
    private String version;
    
    @Arg
    private String input;
    
    @Arg
    private String range;
    
    @Arg
    private VersionPrecedence precedence;

    public Command getCommand() {
        return command;
    }

    public Format getFormat() {
        return format;
    }

    public Version getVersion() {
        return Version.builder().parse(version).precendence(precedence).create();
    }

    public String getInput() {
        return input;
    }

    public Range<Version> getRange() {
        return VersionRange.valueOf(range, precedence);
    }

}
