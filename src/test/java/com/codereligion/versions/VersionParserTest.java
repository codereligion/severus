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

    private final Version unit;
    
    private final String version;
    private final int major;
    private final int minor;
    private final int patch;
    private final String preRelease;
    private final String buildMetadata;

    public VersionParserTest(String version, int major, int minor, int patch,
                             String preRelease, String buildMetadata) {
        this.unit = Version.valueOf(version);
        this.version = version;
        this.major = major;
        this.minor = minor;
        this.patch = patch;
        this.preRelease = preRelease;
        this.buildMetadata = buildMetadata;
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
        assertThat(unit.getMajorVersion(), is(VersionNumber.valueOf(major)));
    }
    
    @Test
    public void majorValue() {
        assertThat(unit.getMajorVersion().getValue(), is(major));
    }

    @Test
    public void minor() {
        assertThat(unit.getMinorVersion(), is(VersionNumber.valueOf(minor)));
    }

    @Test
    public void minorValue() {
        assertThat(unit.getMinorVersion().getValue(), is(minor));
    }

    @Test
    public void patch() {
        assertThat(unit.getPatchVersion(), is(VersionNumber.valueOf(patch)));
    }

    @Test
    public void patchValue() {
        assertThat(unit.getPatchVersion().getValue(), is(patch));
    }

    @Test
    public void preRelease() {
        assertThat(unit.getPreReleaseVersion(), is(PreReleaseVersion.parse(preRelease)));
    }
    
    @Test
    public void preReleaseValue() {
        assertThat(unit.getPreReleaseVersion().toString(), is(preRelease));
    }

    @Test
    public void buildMetadata() {
        assertThat(unit.getBuildMetadata(), is(BuildMetadata.parse(buildMetadata)));
    }

    @Test
    public void buildMetadataValue() {
        assertThat(unit.getBuildMetadata().toString(), is(buildMetadata));
    }

    @Test
    public void equalVersionShouldBeEqual() {
        final Version expected = Version.valueOf(major, minor, patch, preRelease, buildMetadata);
        assertThat(unit, is(expected));
    }

    @Test
    public void equalVersionShouldHaveSameHashCode() {
        final Version expected = Version.valueOf(major, minor, patch, preRelease, buildMetadata);
        assertThat(unit.hashCode(), is(expected.hashCode()));
    }
    
    @Test
    public void equalVersionShouldCompareEqual() {
        final Version expected = Version.valueOf(major, minor, patch, preRelease, buildMetadata);
        assertThat(unit, comparesEqualTo(expected));
    }
    
    @Test
    public void stringValueShouldMatchOriginalInput() {
        assertThat(unit.toString(), is(version));
    }

}
