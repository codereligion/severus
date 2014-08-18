package com.codereligion.severus;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;

import java.util.Arrays;
import java.util.List;

import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertThat;

@RunWith(Parameterized.class)
public final class VersionRangeToStringTest {

    private final VersionRange range;
    private final String value;

    public VersionRangeToStringTest(String range, final String value) {
        this.range = VersionRange.valueOf(range);
        this.value = value;
    }

    @Parameterized.Parameters(name = "{0}")
    public static List<Object[]> data() {
        return Arrays.asList(new Object[][]{
                {"1", "[1.0.0]"},
                {"1.2", "[1.2.0]"},
                {"1.2.3", "[1.2.3]"},
                {"1.2.3-RC1.beta.17", "[1.2.3-RC1.beta.17]"},
                {"1.2.3-RC1.beta.17+5e84d09", "[1.2.3-RC1.beta.17+5e84d09]"},
                {"[1]", "[1.0.0]"},
                {"[1,1]", "[1.0.0]"},
                {"[1,)", "[1.0.0,)"},
                {"(,1]", "(,1.0.0]"},
                {"(1,1]", ""}, // empty ramge
                {"[1,1)", ""}, // empty range
                {"(1,2)", "(1.0.0,2.0.0)"},
                {"[1,2]", "[1.0.0,2.0.0]"},
                {"[1,2)", "[1.0.0,2.0.0)"},
                {"(1,2]", "(1.0.0,2.0.0]"},
                {"(1,)", "(1.0.0,)"},
                {"[1,)", "[1.0.0,)"},
                {"(,2)", "(,2.0.0)"},
                {"(,2]", "(,2.0.0]"},
                {"(,)", "(,)"},
        });
    }

    @Test
    public void test() {
        assertThat(range.toString(), is(value));
    }
    
}
