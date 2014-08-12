package com.codereligion.versions;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;

import java.util.Arrays;

import static org.hamcrest.Matchers.comparesEqualTo;
import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertThat;

@RunWith(Parameterized.class)
public final class VersionParserTest {

    private final Version actual;
    
    private final String version;
    private final int major;
    private final int minor;
    private final int patch;
    private final String preRelease;
    private final String metadata;
    
    private final Version expected;

    public VersionParserTest(String version, int major, int minor, int patch,
                             String preRelease, String metadata) {
        
        this.actual = Version.parse(version);
        
        this.version = version;
        this.major = major;
        this.minor = minor;
        this.patch = patch;
        this.preRelease = preRelease;
        this.metadata = metadata;
        
        this.expected = Version.builder().
                major(major).
                minor(minor).
                patch(patch).
                preRelease(preRelease).
                buildMetadata(metadata).
                build();
    }

    @Parameterized.Parameters(name = "{0}")
    public static Iterable<Object[]> data() {
        return Arrays.asList(new Object[][]{
                {"0.2.1", 0, 2, 1, "", ""},
                {"0.2.0", 0, 2, 0, "", ""},
                {"3.0.0", 3, 0, 0, "", ""},
                {"3.0.1", 3, 0, 1, "", ""},
                {"3.2.0", 3, 2, 0, "", ""},
                {"3.2.1", 3, 2, 1, "", ""},
                {"1.0.0-rc1", 1, 0, 0, "rc1", ""},
                {"1.0.0-rc1-0.5", 1, 0, 0, "rc1-0.5", ""},
                {"1.0.0+5e84d09", 1, 0, 0, "", "5e84d09"},
                {"1.0.0+git.5e84d09", 1, 0, 0, "", "git.5e84d09"},
                {"1.0.0-rc1-0.5+git.5e84d09", 1, 0, 0, "rc1-0.5", "git.5e84d09"},
        });
    }

    @Test
    public void major() {
        assertThat(actual.getMajor(), is(VersionNumber.valueOf(major)));
    }

    @Test
    public void majorValue() {
        assertThat(actual.getMajor().getValue(), is(major));
    }

    @Test
    public void minor() {
        assertThat(actual.getMinor(), is(VersionNumber.valueOf(minor)));
    }

    @Test
    public void minorValue() {
        assertThat(actual.getMinor().getValue(), is(minor));
    }

    @Test
    public void patch() {
        assertThat(actual.getPatch(), is(VersionNumber.valueOf(patch)));
    }

    @Test
    public void patchValue() {
        assertThat(actual.getPatch().getValue(), is(patch));
    }

    @Test
    public void preRelease() {
        assertThat(actual.getPreRelease(), is(PreReleaseVersion.parse(preRelease)));
    }

    @Test
    public void preReleaseValue() {
        assertThat(actual.getPreRelease().toString(), is(preRelease));
    }

    @Test
    public void buildMetadata() {
        assertThat(actual.getBuildMetadata(), is(BuildMetadata.parse(metadata)));
    }

    @Test
    public void buildMetadataValue() {
        assertThat(actual.getBuildMetadata().toString(), is(metadata));
    }

    @Test
    public void equalVersionShouldBeEqual() {
        assertThat(actual, is(expected));
    }

    @Test
    public void equalVersionShouldHaveSameHashCode() {
        assertThat(actual.hashCode(), is(expected.hashCode()));
    }
    
    @Test
    public void equalVersionShouldCompareEqual() {
        assertThat(actual, comparesEqualTo(expected));
    }
    
    @Test
    public void stringValueShouldMatchOriginalInput() {
        assertThat(actual.toString(), is(version));
    }

}
