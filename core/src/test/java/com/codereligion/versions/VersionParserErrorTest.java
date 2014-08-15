package com.codereligion.versions;

import com.google.common.base.Function;
import com.google.common.base.Joiner;
import org.jukito.All;
import org.jukito.JukitoModule;
import org.jukito.JukitoRunner;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;

import java.math.BigInteger;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import static com.google.common.base.Objects.firstNonNull;
import static com.google.common.collect.FluentIterable.from;
import static java.util.Arrays.asList;
import static org.hamcrest.Matchers.comparesEqualTo;
import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertThat;

@RunWith(Parameterized.class)
public final class VersionParserErrorTest {

    private final String version;

    public VersionParserErrorTest(String version) {
        this.version = version;
    }
    
    @Parameterized.Parameters(name = "{0}")
    public static List<Object[]> data() {
        return Arrays.asList(new Object[][] {
                {"1"}, // missing minor+patch
                {"1."}, // missing minor+patch
                {"1.0"}, // missing patch
                {"1.0."}, // missing patch
                {".1.0"}, // missing major
                {"01.2.3"}, // leading zero
                {"1.02.3"}, // leading zero
                {"1.2.03"}, // leading zero
                {"00.2.3"}, // leading zero
                {"1.00.3"}, // leading zero
                {"1.2.00"}, // leading zero
                {"-1.2.3"}, // negative
                {"1.-2.3"}, // negative
                {"1.2.-3"}, // negative
                {"1.2.3-"}, // missing pre-release
                {"1.2.3#"}, // not [0-9A-Za-z-]
                {"1.2.3-$"}, // not [0-9A-Za-z-]
                {"1.2.3-RC1.%"}, // not [0-9A-Za-z-]
                {"1.2.3-RC1.17.&"}, // not [0-9A-Za-z-]
                {"1.2.3-RC1..17"}, // empty identifier
                {"1.2.3-RC1.17."}, // empty identifier
                {"1.2.3-01.17"}, // leading zero 
                {"1.2.3-RC.01"}, // leading zero 
                {"1.2.3-RC1.17#"}, // not [0-9A-Za-z-]
                {"1.2.3-RC1.17+$"}, // not [0-9A-Za-z-]
                {"1.2.3-RC1.17+2014-%"}, // not [0-9A-Za-z-]
                {"1.2.3-RC1.17+2014-08-&"}, // not [0-9A-Za-z-]
                {"1.2.3-RC1.17+2014-08.."}, // empty identifier
                {"1.2.3-RC1.17+2014-08.16."}, // empty identifier
        });
    }
    
    @Test(expected = VersionFormatException.class)
    public void valueOf() {
        Version.valueOf(version);
    }
    
    @Test(expected = VersionFormatException.class)
    public void parse() {
        Version.builder().parse(version);
    }
    
    @Test(expected = VersionFormatException.class)
    public void parsePrecedence() {
        Version.builder().precendence(VersionPrecedence.BUILD).parse(version);
    }
    
}
