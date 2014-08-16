package com.codereligion.versions;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;

import java.util.Arrays;
import java.util.List;

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
    public void valueOfNaturalPrecedence() {
        Version.valueOf(version, VersionPrecedence.NATURAL);
    }
    
    @Test(expected = VersionFormatException.class)
    public void valueOfBuildPrecedence() {
        Version.valueOf(version, VersionPrecedence.BUILD);
    }
    
    @Test(expected = VersionFormatException.class)
    public void parse() {
        Version.builder().parse(version);
    }
    
    @Test(expected = VersionFormatException.class)
    public void parseNaturalPrecedence() {
        Version.builder().precedence(VersionPrecedence.NATURAL).parse(version);
    }
    
    @Test(expected = VersionFormatException.class)
    public void parseBuildPrecedence() {
        Version.builder().precedence(VersionPrecedence.BUILD).parse(version);
    }
    
}
