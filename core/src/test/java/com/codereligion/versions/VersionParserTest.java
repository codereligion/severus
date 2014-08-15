package com.codereligion.versions;

import com.google.common.base.Function;
import com.google.common.base.Joiner;
import org.jukito.All;
import org.jukito.JukitoModule;
import org.jukito.JukitoRunner;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.math.BigInteger;
import java.util.Collections;
import java.util.List;

import static com.google.common.base.Objects.firstNonNull;
import static com.google.common.collect.FluentIterable.from;
import static java.util.Arrays.asList;
import static org.hamcrest.Matchers.comparesEqualTo;
import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertThat;

@RunWith(JukitoRunner.class)
public final class VersionParserTest {

    private static final class Example {
        public final Input input;
        public final Output output;

        private Example(Input input, Output output) {
            this.input = input;
            this.output = output;
        }
    }

    private static final class Input {
        public final String version;

        private Input(String version) {
            this.version = version;
        }
    }

    private static final class Output {
        public final BigInteger major;
        public final BigInteger minor;
        public final BigInteger patch;
        public final Iterable<String> preRelease;
        public final Iterable<String> build;

        private Output(int major, int minor, int patch, List<String> preRelease, List<String> build) {
            this(BigInteger.valueOf(major), BigInteger.valueOf(minor), BigInteger.valueOf(patch), preRelease, build);
        }

        private Output(String major, String minor, String patch, List<String> preRelease, List<String> build) {
            this(new BigInteger(major), new BigInteger(minor), new BigInteger(patch), preRelease, build);
        }

        private Output(BigInteger major, BigInteger minor, BigInteger patch, Iterable<String> preRelease, 
                       Iterable<String> build) {
            this.major = major;
            this.minor = minor;
            this.patch = patch;
            this.preRelease = firstNonNull(preRelease, Collections.<String>emptyList());
            this.build = firstNonNull(build, Collections.<String>emptyList());
        }

        public Version getExpected(VersionPrecedence precedence) {
            return Version.builder().
                    major(major.toString()).
                    minor(minor.toString()).
                    patch(patch.toString()).
                    preRelease(Joiner.on('.').join(preRelease)).
                    build(Joiner.on('.').join(build)).
                    precendence(precedence).
                    create();
        }
    }

    @SuppressWarnings("UnusedDeclaration")
    public static class Module extends JukitoModule {

        @Override
        protected void configureTest() {
            bindManyInstances(Example.class,
                    new Example(
                            new Input("0.2.1"),
                            new Output(0, 2, 1, null, null)),
                    new Example(
                            new Input("0.2.0"),
                            new Output(0, 2, 0, null, null)),
                    new Example(
                            new Input("3.0.0"),
                            new Output(3, 0, 0, null, null)),
                    new Example(
                            new Input("3.0.1"),
                            new Output(3, 0, 1, null, null)),
                    new Example(
                            new Input("3.2.0"),
                            new Output(3, 2, 0, null, null)),
                    new Example(
                            new Input("3.2.1"),
                            new Output(3, 2, 1, null, null)),
                    new Example(
                            new Input("1.0.0-rc1.2"),
                            new Output(1, 0, 0, asList("rc1", "2"), null)),
                    new Example(
                            new Input("1.0.0-rc1-0.5"),
                            new Output(1, 0, 0, asList("rc1-0", "5"), null)),
                    new Example(
                            new Input("1.0.0+5e84d09"),
                            new Output(1, 0, 0, null, asList("5e84d09"))),
                    new Example(
                            new Input("1.0.0+git.5e84d09"),
                            new Output(1, 0, 0, null, asList("git", "5e84d09"))),
                    new Example(
                            new Input("1.0.0-rc1-0.5+git.5e84d09"),
                            new Output(1, 0, 0, asList("rc1-0", "5"), asList("git", "5e84d09"))),
                    new Example(
                            new Input("999999999999.999999999999.999999999999-999999999999"),
                            new Output("999999999999", "999999999999", "999999999999", asList("999999999999"), null)
                    ));

            bindManyInstances(VersionPrecedence.class,
                    VersionPrecedence.NATURAL, VersionPrecedence.BUILD);
        }

    }

    @Test
    public void major(@All Example example, @All VersionPrecedence precedence) {
        final Version unit = Version.builder().parse(example.input.version).precendence(precedence).create();
        assertThat(unit.getMajor(), is(VersionNumber.valueOf(example.output.major.toString())));
    }

    @Test
    public void majorValue(@All Example example, @All VersionPrecedence precedence) {
        final Version unit = Version.builder().parse(example.input.version).precendence(precedence).create();
        assertThat(unit.getMajor().getValue(), comparesEqualTo(example.output.major));
    }

    @Test
    public void minor(@All Example example, @All VersionPrecedence precedence) {
        final Version unit = Version.builder().parse(example.input.version).precendence(precedence).create();
        assertThat(unit.getMinor(), is(VersionNumber.valueOf(example.output.minor.toString())));
    }

    @Test
    public void minorValue(@All Example example, @All VersionPrecedence precedence) {
        final Version unit = Version.builder().parse(example.input.version).precendence(precedence).create();
        assertThat(unit.getMinor().getValue(), comparesEqualTo(example.output.minor));
    }

    @Test
    public void patch(@All Example example, @All VersionPrecedence precedence) {
        final Version unit = Version.builder().parse(example.input.version).precendence(precedence).create();
        assertThat(unit.getPatch(), is(VersionNumber.valueOf(example.output.patch.toString())));
    }

    @Test
    public void patchValue(@All Example example, @All VersionPrecedence precedence) {
        final Version unit = Version.builder().parse(example.input.version).precendence(precedence).create();
        assertThat(unit.getPatch().getValue(), comparesEqualTo(example.output.patch));
    }

    @Test
    public void preRelease(@All Example example, @All VersionPrecedence precedence) {
        final Version unit = Version.builder().parse(example.input.version).precendence(precedence).create();
        assertThat(unit.getPreRelease(), is(PreReleaseVersion.valueOf(example.output.preRelease)));
    }

    @Test
    public void preReleaseValue(@All Example example, @All VersionPrecedence precedence) {
        final Version unit = Version.builder().parse(example.input.version).precendence(precedence).create();
        assertThat(from(unit.getPreRelease()).transform(toValue()).toList(), is(example.output.preRelease));
    }

    @Test
    public void build(@All Example example, @All VersionPrecedence precedence) {
        final Version unit = Version.builder().parse(example.input.version).precendence(precedence).create();
        assertThat(unit.getBuild(), is(BuildMetadata.valueOf(example.output.build)));
    }

    @Test
    public void buildValue(@All Example example, @All VersionPrecedence precedence) {
        final Version unit = Version.builder().parse(example.input.version).precendence(precedence).create();
        assertThat(from(unit.getBuild()).transform(toValue()).toList(), is(example.output.build));
    }

    @Test
    public void equalVersionShouldBeEqual(@All Example example, @All VersionPrecedence precedence) {
        final Version unit = Version.builder().parse(example.input.version).precendence(precedence).create();
        assertThat(unit, is(example.output.getExpected(precedence)));
    }

    @Test
    public void equalVersionShouldHaveSameHashCode(@All Example example, @All VersionPrecedence precedence) {
        final Version unit = Version.builder().parse(example.input.version).precendence(precedence).create();
        assertThat(unit.hashCode(), is(example.output.getExpected(precedence).hashCode()));
    }

    @Test
    public void equalVersionShouldCompareEqual(@All Example example, @All VersionPrecedence precedence) {
        final Version unit = Version.builder().parse(example.input.version).precendence(precedence).create();
        assertThat(unit, comparesEqualTo(example.output.getExpected(precedence)));
    }

    @Test
    public void stringValueShouldMatchOriginalInput(@All Example example, @All VersionPrecedence precedence) {
        final Version unit = Version.builder().parse(example.input.version).precendence(precedence).create();
        assertThat(unit.toString(), is(example.input.version));
    }

    private Function<Identifier<?>, String> toValue() {
        return new Function<Identifier<?>, String>() {
            @Override
            public String apply(Identifier<?> input) {
                return input.getValue().toString();
            }
        };
    }

}
