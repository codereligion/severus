package com.codereligion.versions;

import com.google.common.collect.BoundType;
import com.google.common.collect.Range;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;

import java.util.Arrays;

import static com.google.common.collect.BoundType.CLOSED;
import static com.google.common.collect.BoundType.OPEN;
import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertThat;

@RunWith(Parameterized.class)
public final class VersionRangeTest {
    
    private final Range<Version> unit;
    
    private final BoundType lowerBoundType;
    private final Version lowerEndpoint;
    private final Version upperEndpoint;
    private final BoundType upperBoundType;

    public VersionRangeTest(String range, BoundType lowerBoundType, String lowerEndpoint, String upperEndpoint, 
                            BoundType upperBoundType) {
        
        this.unit = VersionRange.valueOf(range);
        this.lowerBoundType = lowerBoundType;
        this.lowerEndpoint = Version.valueOf(lowerEndpoint);
        this.upperEndpoint = Version.valueOf(upperEndpoint);
        this.upperBoundType = upperBoundType;
    }

    @Parameterized.Parameters(name = "{0}")
    public static Iterable<Object[]> data() {
        return Arrays.asList(new Object[][]{
                {"(1.0.0,2.0.0)", OPEN, "1.0.0", "2.0.0", OPEN},
                {"[1.0.0,2.0.0]", CLOSED, "1.0.0", "2.0.0", CLOSED},
                {"[1.0.0,2.0.0)", CLOSED, "1.0.0", "2.0.0", OPEN},
                {"(1.0.0,2.0.0]", OPEN, "1.0.0", "2.0.0", CLOSED},
                {"(1.0.0,)", OPEN, "1.0.0", null, OPEN},
                {"[1.0.0,)", CLOSED, "1.0.0", null, OPEN},
                {"(,2.0.0)", OPEN, null, "2.0.0", OPEN},
                {"(,2.0.0]", OPEN, null, "2.0.0", CLOSED},
                {"(,)", OPEN, null, null, OPEN},
                {"1.0.0", CLOSED, "1.0.0", "1.0.0", CLOSED},
                {"[1.0.0,1.0.0)", CLOSED, "1.0.0", "1.0.0", OPEN},
                {"(1.0.0,1.0.0]", OPEN, "1.0.0", "1.0.0", CLOSED},
        });
    }

    @Test
    public void hasLowerBound() {
        assertThat(unit.hasLowerBound(), is(lowerEndpoint != null));
    }
    
    @Test
    public void lowerBoundType() {
        if (unit.hasLowerBound()) {
            assertThat(unit.lowerBoundType(), is(lowerBoundType));
        }
    }
    
    @Test
    public void lowerEndpoint() {
        if (unit.hasLowerBound()) {
            assertThat(unit.lowerEndpoint(), is(lowerEndpoint));
        }
    }

    @Test
    public void hasUpperBound() {
        assertThat(unit.hasUpperBound(), is(upperEndpoint != null));
    }
    
    @Test
    public void upperBoundType() {
        if (unit.hasUpperBound()) {
            assertThat(unit.upperBoundType(), is(upperBoundType));
        }
    }
    
    @Test
    public void upperEndpoint() {
        if (unit.hasUpperBound()) {
            assertThat(unit.upperEndpoint(), is(upperEndpoint));
        }
    }
    
}
