package com.codereligion.versions;

import com.google.common.collect.Range;
import net.sourceforge.argparse4j.annotation.Arg;

final class Options {
    
    @Arg
    private Command command;
    
    @Arg
    private Format format;
    
    @Arg
    private Version version;
    
    @Arg
    private String input;
    
    @Arg
    private Range<Version> range;

    public Command getCommand() {
        return command;
    }

    public Format getFormat() {
        return format;
    }

    public Version getVersion() {
        return version;
    }

    public String getInput() {
        return input;
    }

    public Range<Version> getRange() {
        return range;
    }
    
}
