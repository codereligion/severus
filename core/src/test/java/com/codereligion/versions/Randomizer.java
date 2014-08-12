package com.codereligion.versions;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.Lists;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

final class Randomizer {
    
    private Randomizer() {
        // utility class
    }

    public static Iterable<Object[]> randomize(List<Version> versions, int max) {
        final List<Object[]> data = Lists.newArrayListWithExpectedSize(max);
        
        for (int i = 0; i < max; i++) {
            final List<Version> list = new ArrayList<>(versions);
            Collections.shuffle(list);
            
            data.add(new Object[] {ImmutableList.copyOf(list)});
        }
     
        return data;
    }
    
}
