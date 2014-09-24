package com.codereligion.severus;

final class ValidateCommand implements Command {

    @Override
    public int execute(Options options) {
        try {
            Version.valueOf(options.getInput());
            return 0;
        } catch (VersionFormatException e) {
            if (options.isVerbose()) {
                System.err.println(e.getMessage());
            }
            return 1;
        }
    }
    
}
