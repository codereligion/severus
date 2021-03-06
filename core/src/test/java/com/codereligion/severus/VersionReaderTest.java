package com.codereligion.severus;

import com.google.common.base.Function;
import com.google.common.base.Joiner;
import com.google.common.collect.ImmutableSet;
import com.google.common.collect.Lists;
import com.google.common.collect.Sets;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;

import javax.annotation.Nullable;
import java.math.BigInteger;
import java.util.Collections;
import java.util.List;
import java.util.Set;

import static com.google.common.base.Objects.firstNonNull;
import static com.google.common.collect.FluentIterable.from;
import static java.util.Arrays.asList;
import static org.hamcrest.Matchers.comparesEqualTo;
import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertThat;

@RunWith(Parameterized.class)
public final class VersionReaderTest {
    
    private final Example example;
    private final VersionPrecedence precedence;

    public VersionReaderTest(Example example, VersionPrecedence precedence) {
        this.example = example;
        this.precedence = precedence;
    }

    private static final class Example {
        public final Input input;
        public final Output output;

        private Example(Input input, Output output) {
            this.input = input;
            this.output = output;
        }

        @Override
        public String toString() {
            return input.version;
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

        private Output(int major, int minor, int patch, @Nullable List<String> preRelease, 
                       @Nullable Iterable<String> build) {
            this(BigInteger.valueOf(major), BigInteger.valueOf(minor), BigInteger.valueOf(patch), preRelease, build);
        }

        private Output(String major, String minor, String patch, @Nullable Iterable<String> preRelease, 
                       @Nullable Iterable<String> build) {
            this(new BigInteger(major), new BigInteger(minor), new BigInteger(patch), preRelease, build);
        }

        private Output(BigInteger major, BigInteger minor, BigInteger patch, @Nullable Iterable<String> preRelease, 
                       @Nullable Iterable<String> build) {
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
                    precedence(precedence).
                    create();
        }
    }
    
    @Parameterized.Parameters(name = "{0} ({1})")
    public static List<Object[]> data() {
        final ImmutableSet<Example> examples = ImmutableSet.of(new Example(
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
                new Example( // integer overflow
                        new Input("999999999999.999999999999.999999999999-999999999999"),
                        new Output("999999999999", "999999999999", "999999999999", asList("999999999999"), null))
        );

        final ImmutableSet<VersionPrecedence> precedences = ImmutableSet.of(
                VersionPrecedence.NATURAL, 
                VersionPrecedence.BUILD);

        final Set<List<Object>> lists = Sets.cartesianProduct(examples, precedences);
        final List<Object[]> objects = Lists.newArrayListWithExpectedSize(lists.size());

        for (List<Object> list : lists) {
            objects.add(list.toArray());
        }

        return objects;
    }

    @Test
    public void major() {
        final Version unit = Version.valueOf(example.input.version, precedence);
        assertThat(unit.getMajor(), is(VersionNumber.valueOf(example.output.major)));
    }

    @Test
    public void majorValue() {
        final Version unit = Version.valueOf(example.input.version, precedence);
        assertThat(unit.getMajor().getValue(), comparesEqualTo(example.output.major));
    }

    @Test
    public void minor() {
        final Version unit = Version.valueOf(example.input.version, precedence);
        assertThat(unit.getMinor(), is(VersionNumber.valueOf(example.output.minor)));
    }

    @Test
    public void minorValue() {
        final Version unit = Version.valueOf(example.input.version, precedence);
        assertThat(unit.getMinor().getValue(), comparesEqualTo(example.output.minor));
    }

    @Test
    public void patch() {
        final Version unit = Version.valueOf(example.input.version, precedence);
        assertThat(unit.getPatch(), is(VersionNumber.valueOf(example.output.patch)));
    }

    @Test
    public void patchValue() {
        final Version unit = Version.valueOf(example.input.version, precedence);
        assertThat(unit.getPatch().getValue(), comparesEqualTo(example.output.patch));
    }

    @Test
    public void preRelease() {
        final Version unit = Version.valueOf(example.input.version, precedence);
        assertThat(unit.getPreRelease(), is(PreReleaseVersion.valueOf(example.output.preRelease)));
    }

    @Test
    public void preReleaseValue() {
        final Version unit = Version.valueOf(example.input.version, precedence);
        assertThat(from(unit.getPreRelease()).transform(toValue()).toList(), is(example.output.preRelease));
    }

    @Test
    public void build() {
        final Version unit = Version.valueOf(example.input.version, precedence);
        assertThat(unit.getBuild(), is(BuildMetadata.valueOf(example.output.build)));
    }

    @Test
    public void buildValue() {
        final Version unit = Version.valueOf(example.input.version, precedence);
        assertThat(from(unit.getBuild()).transform(toValue()).toList(), is(example.output.build));
    }

    @Test
    public void equalVersionShouldBeEqual() {
        final Version unit = Version.valueOf(example.input.version, precedence);
        assertThat(unit, is(example.output.getExpected(precedence)));
    }

    @Test
    public void equalVersionShouldHaveSameHashCode() {
        final Version unit = Version.valueOf(example.input.version, precedence);
        assertThat(unit.hashCode(), is(example.output.getExpected(precedence).hashCode()));
    }

    @Test
    public void equalVersionShouldCompareEqual() {
        final Version unit = Version.valueOf(example.input.version, precedence);
        assertThat(unit, comparesEqualTo(example.output.getExpected(precedence)));
    }

    @Test
    public void stringValueShouldMatchOriginalInput() {
        final Version unit = Version.valueOf(example.input.version, precedence);
        assertThat(unit.toString(), is(example.input.version));
    }

    private Function<Identifier<?>, String> toValue() {
        return new NullHostileFunction<Identifier<?>, String>() {
            @Override
            public String apply(Identifier<?> input) {
                return input.getValue().toString();
            }
        };
    }

}
