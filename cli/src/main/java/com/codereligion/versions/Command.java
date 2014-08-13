package com.codereligion.versions;

import java.io.IOException;

interface Command {

    int execute(Options options) throws IOException;

}
