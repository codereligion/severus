package com.codereligion.versions;

import com.google.common.base.Function;

interface NullHostileFunction<F, T> extends Function<F, T> {

    @Override
    @SuppressWarnings("NullableProblems")
    T apply(F input);
    
}
