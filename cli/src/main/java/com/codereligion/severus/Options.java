package com.codereligion.severus;

import net.sourceforge.argparse4j.annotation.Arg;

final class Options {
    
    @Arg
    private Command command;

    @Arg
    private Format format;

    @Arg
    private boolean pretty;

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

    public boolean isPretty() {
        return pretty;
    }

    public Version getVersion() {
        return Version.valueOf(version, precedence);
    }

    public String getInput() {
        return input;
    }

    public VersionRange getRange() {
        return VersionRange.valueOf(range, precedence);
    }
    
}
