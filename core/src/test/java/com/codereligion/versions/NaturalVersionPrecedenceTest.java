package com.codereligion.versions;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.Ordering;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;

import java.util.Comparator;

import static com.codereligion.versions.VersionPrecedence.NATURAL;
import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertThat;

@RunWith(Parameterized.class)
public final class NaturalVersionPrecedenceTest {

    private static final ImmutableList<Version> NATURALS = ImmutableList.of(
            Version.valueOf("1.0.0-alpha+001"),
            Version.valueOf("1.0.0-alpha+002"),
            Version.valueOf("1.0.0-alpha"),
            Version.valueOf("1.0.0-alpha.1"),
            Version.valueOf("1.0.0-alpha.beta"),
            Version.valueOf("1.0.0-beta"),
            Version.valueOf("1.0.0-beta+exp.sha.5e84d09"),
            Version.valueOf("1.0.0-beta+exp.sha.5114f85"),
            Version.valueOf("1.0.0-beta.2"),
            Version.valueOf("1.0.0-beta.11"),
            Version.valueOf("1.0.0-rc.1"),
            Version.valueOf("1.0.0+20140813002312"),
            Version.valueOf("1.0.0"),
            Version.valueOf("1.0.0+20130313144700"));
    
    private final ImmutableList<Version> input;

    public NaturalVersionPrecedenceTest(ImmutableList<Version> input) {
        this.input = input;
    }

    @Parameterized.Parameters
    public static Iterable<Object[]> data() {
        return Randomizer.randomize(NATURALS, 100);
    }
    
    private ImmutableList<Version> sort(Comparator<Version> precedence) {
        return Ordering.from(precedence).immutableSortedCopy(input);
    }

    @Test
    public void natural() {
        assertThat(sort(NATURAL), is(NATURALS));
    }
    
}
