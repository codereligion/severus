package com.codereligion.severus;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;

import java.util.List;

import static com.google.common.collect.FluentIterable.from;
import static java.util.Arrays.asList;
import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertThat;

@RunWith(Parameterized.class)
public final class VersionRangeParserTest {

    // TODO precedence?
    private final VersionRange unit;

    private final Iterable<Version> includes;
    private final Iterable<Version> excludes;

    public VersionRangeParserTest(String range, List<String> includes, List<String> excludes) {
        this.unit = VersionRange.valueOf(range);
        this.includes = from(includes).transform(Version.converter());
        this.excludes = from(excludes).transform(Version.converter());
    }

    @Parameterized.Parameters(name = "{0}")
    public static Iterable<Object[]> data() {
        return asList(new Object[][]{
                {"(1.0.0,2.0.0)",
                        asList("1.0.1", "1.9.9"),
                        asList("0.9.9", "1.0.0", "2.0.0", "2.0.1")},
                {"(1.1,2.0)",
                        asList("1.9.9"),
                        asList("0.9.9", "1.0.0", "1.0.1", "2.0.0", "2.0.1")},
                {"(1,2)",
                        asList("1.0.1", "1.9.9"),
                        asList("0.9.9", "1.0.0", "2.0.0", "2.0.1")},
                {"[1.0.0,2.0.0]",
                        asList("1.0.0", "1.0.1", "1.9.9", "2.0.0"),
                        asList("0.9.9", "2.0.1")},
                {"[1.1,2.0]",
                        asList("1.9.9", "2.0.0"),
                        asList("0.9.9", "1.0.0", "1.0.1", "2.0.1")},
                {"[1,2]",
                        asList("1.0.0", "1.0.1", "1.9.9", "2.0.0"),
                        asList("0.9.9", "2.0.1")},
                {"[1.0.0,2.0.0)",
                        asList("1.0.0", "1.0.1", "1.9.9"),
                        asList("0.9.9", "2.0.0", "2.0.1")},
                {"[1.1,2.0)",
                        asList("1.9.9"),
                        asList("0.9.9", "1.0.0", "1.0.1", "2.0.0", "2.0.1")},
                {"[1,2)",
                        asList("1.0.0", "1.0.1", "1.9.9"),
                        asList("0.9.9", "2.0.0", "2.0.1")},
                {"(1.0.0,2.0.0]",
                        asList("1.0.1", "1.9.9", "2.0.0"),
                        asList("0.9.9", "1.0.0", "2.0.1")},
                {"(1.1,2.0]",
                        asList("1.9.9", "2.0.0"),
                        asList("0.9.9", "1.0.0", "1.0.1", "2.0.1")},
                {"(1,2]",
                        asList("1.0.1", "1.9.9", "2.0.0"),
                        asList("0.9.9", "1.0.0", "2.0.1")},
                {"(1.0.0,)",
                        asList("1.0.1", "1.9.9", "2.0.0", "2.0.1"),
                        asList("0.9.9", "1.0.0")},
                {"(1.1,)",
                        asList("1.9.9", "2.0.0", "2.0.1"),
                        asList("0.9.9", "1.0.0", "1.0.1")},
                {"(1,)",
                        asList("1.0.1", "1.9.9", "2.0.0", "2.0.1"),
                        asList("0.9.9", "1.0.0")},
                {"[1.0.0,)",
                        asList("1.0.0", "1.0.1", "1.9.9", "2.0.0", "2.0.1"),
                        asList("0.9.9")},
                {"[1.1,)",
                        asList("1.9.9", "2.0.0", "2.0.1"),
                        asList("0.9.9", "1.0.0", "1.0.1")},
                {"[1,)",
                        asList("1.0.0", "1.0.1", "1.9.9", "2.0.0", "2.0.1"),
                        asList("0.9.9")},
                {"(,2.0.0)",
                        asList("0.9.9", "1.0.0", "1.0.1", "1.9.9"),
                        asList("2.0.0", "2.0.1")},
                {"(,2.1)",
                        asList("0.9.9", "1.0.0", "1.0.1", "1.9.9", "2.0.0", "2.0.1"),
                        asList()},
                {"(,2)",
                        asList("0.9.9", "1.0.0", "1.0.1", "1.9.9"),
                        asList("2.0.0", "2.0.1")},
                {"(,2.0.0]",
                        asList("0.9.9", "1.0.0", "1.0.1", "1.9.9", "2.0.0"),
                        asList("2.0.1")},
                {"(,2.1]",
                        asList("0.9.9", "1.0.0", "1.0.1", "1.9.9", "2.0.0", "2.0.1"),
                        asList()},
                {"(,2]",
                        asList("0.9.9", "1.0.0", "1.0.1", "1.9.9", "2.0.0"),
                        asList("2.0.1")},
                {"(,)",
                        asList("0.9.9", "1.0.0", "1.0.1", "1.9.9", "2.0.0", "2.0.1"),
                        asList()},
                {"1.0.0",
                        asList("1.0.0"),
                        asList("0.9.9", "1.0.1", "1.9.9", "2.0.0", "2.0.1")},
                {"1.0",
                        asList("1.0.0"),
                        asList("0.9.9", "1.0.1", "1.9.9", "2.0.0", "2.0.1")},
                {"1",
                        asList("1.0.0"),
                        asList("0.9.9", "1.0.1", "1.9.9", "2.0.0", "2.0.1")},
                {"[1.0.0,1.0.0]",
                        asList("1.0.0"),
                        asList("0.9.9", "1.0.1", "1.9.9", "2.0.0", "2.0.1")},
                {"[1.0,1.0]",
                        asList("1.0.0"),
                        asList("0.9.9", "1.0.1", "1.9.9", "2.0.0", "2.0.1")},
                {"[1,1]",
                        asList("1.0.0"),
                        asList("0.9.9", "1.0.1", "1.9.9", "2.0.0", "2.0.1")},
                {"[1.0.0]",
                        asList("1.0.0"),
                        asList("0.9.9", "1.0.1", "1.9.9", "2.0.0", "2.0.1")},
                {"[1.0]",
                        asList("1.0.0"),
                        asList("0.9.9", "1.0.1", "1.9.9", "2.0.0", "2.0.1")},
                {"[1]",
                        asList("1.0.0"),
                        asList("0.9.9", "1.0.1", "1.9.9", "2.0.0", "2.0.1")},
                {"[1.0.0,1.0.0)",
                        asList(),
                        asList("0.9.9", "1.0.0", "1.0.1", "1.9.9", "2.0.0", "2.0.1")},
                {"[1.0,1.0)",
                        asList(),
                        asList("0.9.9", "1.0.0", "1.0.1", "1.9.9", "2.0.0", "2.0.1")},
                {"[1,1)",
                        asList(),
                        asList("0.9.9", "1.0.0", "1.0.1", "1.9.9", "2.0.0", "2.0.1")},
                {"(1.0.0,1.0.0]",
                        asList(),
                        asList("0.9.9", "1.0.0", "1.0.1", "1.9.9", "2.0.0", "2.0.1")},
                {"(1.0,1.0]",
                        asList(),
                        asList("0.9.9", "1.0.0", "1.0.1", "1.9.9", "2.0.0", "2.0.1")},
                {"(1,1]",
                        asList(),
                        asList("0.9.9", "1.0.0", "1.0.1", "1.9.9", "2.0.0", "2.0.1")},
                {"", // empty range
                        asList(),
                        asList("0.9.9", "1.0.0", "1.0.1", "1.9.9", "2.0.0", "2.0.1")},
                {"(,1.0.0],[1.1.0,)",
                        asList("0.9.9", "1.0.0", "1.9.9", "2.0.0", "2.0.1"),
                        asList("1.0.1")},
                {"(,1.0],[1.1,)",
                        asList("0.9.9", "1.0.0", "1.9.9", "2.0.0", "2.0.1"),
                        asList("1.0.1")},
                {"(,1.0.0),(1.0.0,)",
                        asList("0.9.9", "1.0.1", "1.9.9", "2.0.0", "2.0.1"),
                        asList("1.0.0")},
                {"(,1.0),(1.0,)",
                        asList("0.9.9", "1.0.1", "1.9.9", "2.0.0", "2.0.1"),
                        asList("1.0.0")},
                {"(,1),(1,)",
                        asList("0.9.9", "1.0.1", "1.9.9", "2.0.0", "2.0.1"),
                        asList("1.0.0")},
                {"(,1),[1.9.9],(2,)",
                        asList("0.9.9", "1.9.9", "2.0.1"),
                        asList("1.0.0", "1.0.1", "2.0.0")},
                {"(,1),1.9.9,(2,)",
                        asList("0.9.9", "1.9.9", "2.0.1"),
                        asList("1.0.0", "1.0.1", "2.0.0")}
        });
    }

    @Test
    public void includes() {
        for (Version include : includes) {
            assertThat("Expected " + unit + " to include " + include, unit.contains(include), is(true));
        }
    }

    @Test
    public void excludes() {
        for (Version exclude : excludes) {
            assertThat("Expected " + unit + " to not include " + exclude, unit.contains(exclude), is(false));
        }
    }

}
