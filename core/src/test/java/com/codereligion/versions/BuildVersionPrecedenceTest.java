package com.codereligion.versions;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.Ordering;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;

import static com.codereligion.versions.VersionPrecedence.BUILD;
import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertThat;

@RunWith(Parameterized.class)
public final class BuildVersionPrecedenceTest {

    private static final ImmutableList<Version> BUILDS = ImmutableList.of(
            Version.valueOf("1.0.0-alpha+001", BUILD),
            Version.valueOf("1.0.0-alpha+002", BUILD),
            Version.valueOf("1.0.0-alpha", BUILD),
            Version.valueOf("1.0.0-alpha.1", BUILD),
            Version.valueOf("1.0.0-alpha.beta", BUILD),
            Version.valueOf("1.0.0-beta+exp.sha.5114f85", BUILD),
            Version.valueOf("1.0.0-beta+exp.sha.5e84d09", BUILD),
            Version.valueOf("1.0.0-beta", BUILD),
            Version.valueOf("1.0.0-beta.2", BUILD),
            Version.valueOf("1.0.0-beta.11", BUILD),
            Version.valueOf("1.0.0-rc.1", BUILD),
            Version.valueOf("1.0.0+20130313144700", BUILD),
            Version.valueOf("1.0.0+20140813002312", BUILD),
            Version.valueOf("1.0.0", BUILD));

    private final ImmutableList<Version> input;

    public BuildVersionPrecedenceTest(ImmutableList<Version> input) {
        this.input = input;
    }

    @Parameterized.Parameters
    public static Iterable<Object[]> data() {
        return Randomizer.randomize(BUILDS, 100);
    }

    private ImmutableList<Version> sort() {
        return Ordering.from(BUILD).immutableSortedCopy(input);
    }

    @Test
    public void build() {
        assertThat(sort(), is(BUILDS));
    }

}
