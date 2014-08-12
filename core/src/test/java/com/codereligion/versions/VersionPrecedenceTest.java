package com.codereligion.versions;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.Lists;
import com.google.common.collect.Ordering;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertThat;

@RunWith(Parameterized.class)
public final class VersionPrecedenceTest {

    private static final int MAX = 100;
    
    private static final ImmutableList<Version> EXPECTED = ImmutableList.of(
            VersionParser.parse("1.0.0-alpha"),
            VersionParser.parse("1.0.0-alpha.1"),
            VersionParser.parse("1.0.0-alpha.beta"),
            VersionParser.parse("1.0.0-beta"),
            VersionParser.parse("1.0.0-beta.2"),
            VersionParser.parse("1.0.0-beta.11"),
            VersionParser.parse("1.0.0-rc.1"),
            VersionParser.parse("1.0.0"));

    private final Ordering<Version> unit = new VersionPrecedence();
    private final ImmutableList<Version> input;

    public VersionPrecedenceTest(ImmutableList<Version> input) {
        this.input = input;
    }

    @Parameterized.Parameters
    public static Iterable<Object[]> data() {
        final List<Object[]> data = Lists.newArrayListWithExpectedSize(MAX);
        
        for (int i = 0; i < MAX; i++) {
            final List<Version> list = new ArrayList<>(EXPECTED);
            Collections.shuffle(list);
            
            data.add(new Object[] {ImmutableList.copyOf(list)});
        }
     
        return data;
    }

    @Test
    public void test() {
        assertThat(unit.immutableSortedCopy(input), is(EXPECTED));
    }

}
