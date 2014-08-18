package com.codereligion.severus;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;

import java.util.Arrays;
import java.util.List;

import static com.codereligion.severus.Serializer.copy;
import static com.codereligion.severus.VersionPrecedence.BUILD;
import static org.hamcrest.Matchers.comparesEqualTo;
import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertThat;

@RunWith(Parameterized.class)
public final class VersionSerializationTest {

    private final Version expected;
    private final Version actual;

    public VersionSerializationTest(Version expected) {
        this.expected = expected;
        this.actual = copy(expected);
    }

    @Parameterized.Parameters(name = "{0}")
    public static List<Object[]> data() {
        return Arrays.asList(new Object[][]{
                {Version.valueOf("1.2.3-RC1.beta.17+5e84d09")},
                {Version.valueOf("1.2.3-RC1.beta.18+5e84d09", BUILD)}
        });
    }

    @Test
    public void major() {
        assertThat(actual.getMajor(), is(expected.getMajor()));
    }
    
    @Test
    public void minor() {
        assertThat(actual.getMinor(), is(expected.getMinor()));
    }
    
    @Test
    public void patch() {
        assertThat(actual.getPatch(), is(expected.getPatch()));
    }
    
    @Test
    public void preRelease() {
        assertThat(actual.getPreRelease(), is(expected.getPreRelease()));
    }
    
    @Test
    public void build() {
        assertThat(actual.getBuild(), is(expected.getBuild()));
    }
    
    @Test
    public void precedence() {
        assertThat(actual.getPrecedence(), is(expected.getPrecedence()));
    }
    
    @Test
    public void shouldBeEquals() {
        assertThat(actual, is(expected));
    }
    
    @Test
    public void shouldCompareEqualTo() {
        assertThat(actual, comparesEqualTo(expected));
    }
    
    @Test
    public void shouldHaveSameHashCode() {
        assertThat(actual.hashCode(), is(expected.hashCode()));
    }

}
