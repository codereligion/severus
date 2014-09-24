package com.codereligion.severus;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;

import java.util.Arrays;
import java.util.List;

@RunWith(Parameterized.class)
public final class VersionRangeReaderExceptionTest {
    
    private final String range;

    public VersionRangeReaderExceptionTest(String range) {
        this.range = range;
    }

    @Parameterized.Parameters(name = "{0}")
    public static List<Object[]> data() {
        return Arrays.asList(new Object[][]{
            {"(1,1)"},
            {"(,1],[1,)"},
            {"[,1)"},
            {"(1,]"},
        });
    }
    
    @Test(expected = VersionFormatException.class)
    public void testWithNaturalPrecedence() {
        VersionRange.valueOf(range, VersionPrecedence.NATURAL);
    }
    
    @Test(expected = VersionFormatException.class)
    public void testWithBuildPrecedence() {
        VersionRange.valueOf(range, VersionPrecedence.BUILD);
    }

}
