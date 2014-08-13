package com.codereligion.versions;

final class ValidateCommand implements Command {

    @Override
    public int execute(Options options) {
        try {
            Version.valueOf(options.getInput());
            return 0;
        } catch (VersionFormatException e) {
            return 1;
        }
    }
    
}
