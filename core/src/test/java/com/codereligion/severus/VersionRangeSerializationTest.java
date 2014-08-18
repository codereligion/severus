package com.codereligion.severus;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;

import java.util.Arrays;
import java.util.List;

import static com.codereligion.severus.Serializer.copy;
import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertThat;

@RunWith(Parameterized.class)
public final class VersionRangeSerializationTest {

    private final String range;

    public VersionRangeSerializationTest(String range) {
        this.range = range;
    }

    @Parameterized.Parameters(name = "{0}")
    public static List<Object[]> data() {
        return Arrays.asList(new Object[][]{
                {"1"},
                {"1.2"},
                {"1.2.3"},
                {"1.2.3-RC1.beta.17"},
                {"1.2.3-RC1.beta.17+5e84d09"},
                {"[1]"},
                {"[1,1]"},
                {"[1,)"},
                {"(,1]"},
                {"(1,1]"},
                {"[1,1)"},
                {"(1,2)"},
                {"[1,2]"},
                {"[1,2)"},
                {"(1,2]"},
                {"(1,)"},
                {"[1,)"},
                {"(,2)"},
                {"(,2]"},
                {"(,)"},
        });
    }

    @Test
    public void test() {
        final VersionRange expected = VersionRange.valueOf(range);
        final VersionRange actual = copy(expected);

        assertThat(expected, is(actual));
    }
    
}
