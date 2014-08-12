package com.codereligion.versions;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.Ordering;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;

import java.util.Comparator;

import static com.codereligion.versions.VersionPrecedence.BUILD;
import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertThat;

@RunWith(Parameterized.class)
public final class BuildVersionPrecedenceTest {

    private static final ImmutableList<Version> BUILDS = ImmutableList.of(
            Version.builder().parse("1.0.0-alpha+001").precendence(BUILD).create(),
            Version.builder().parse("1.0.0-alpha+002").precendence(BUILD).create(),
            Version.builder().parse("1.0.0-alpha").precendence(BUILD).create(),
            Version.builder().parse("1.0.0-alpha.1").precendence(BUILD).create(),
            Version.builder().parse("1.0.0-alpha.beta").precendence(BUILD).create(),
            Version.builder().parse("1.0.0-beta+exp.sha.5114f85").precendence(BUILD).create(),
            Version.builder().parse("1.0.0-beta+exp.sha.5e84d09").precendence(BUILD).create(),
            Version.builder().parse("1.0.0-beta").precendence(BUILD).create(),
            Version.builder().parse("1.0.0-beta.2").precendence(BUILD).create(),
            Version.builder().parse("1.0.0-beta.11").precendence(BUILD).create(),
            Version.builder().parse("1.0.0-rc.1").precendence(BUILD).create(),
            Version.builder().parse("1.0.0+20130313144700").precendence(BUILD).create(),
            Version.builder().parse("1.0.0+20140813002312").precendence(BUILD).create(),
            Version.builder().parse("1.0.0").precendence(BUILD).create());

    private final ImmutableList<Version> input;

    public BuildVersionPrecedenceTest(ImmutableList<Version> input) {
        this.input = input;
    }

    @Parameterized.Parameters
    public static Iterable<Object[]> data() {
        return Randomizer.randomize(BUILDS, 100);
    }

    private ImmutableList<Version> sort(Comparator<Version> precedence) {
        return Ordering.from(precedence).immutableSortedCopy(input);
    }

    @Test
    public void build() {
        assertThat(sort(BUILD), is(BUILDS));
    }

}
