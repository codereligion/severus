package com.codereligion.versions;

import com.google.common.base.Joiner;
import net.sourceforge.argparse4j.inf.ArgumentChoice;

final class FormatChoice implements ArgumentChoice {

    @Override
    public boolean contains(Object val) {
        return true;
    }

    @Override
    public String textualFormat() {
        return Joiner.on(", ").join(Format.choices());
    }


}
