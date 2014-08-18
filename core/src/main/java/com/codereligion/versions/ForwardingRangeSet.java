package com.codereligion.versions;

import com.google.common.collect.ForwardingObject;
import com.google.common.collect.Range;
import com.google.common.collect.RangeSet;

import java.util.Set;

abstract class ForwardingRangeSet<T extends Comparable> extends ForwardingObject implements RangeSet<T> {

    @Override
    protected abstract RangeSet<T> delegate();

    @Override
    public boolean contains(T value) {
        return delegate().contains(value);
    }

    @Override
    public Range<T> rangeContaining(T value) {
        return delegate().rangeContaining(value);
    }

    @Override
    public boolean encloses(Range<T> otherRange) {
        return delegate().encloses(otherRange);
    }

    @Override
    public boolean enclosesAll(RangeSet<T> other) {
        return delegate().enclosesAll(other);
    }

    @Override
    public boolean isEmpty() {
        return delegate().isEmpty();
    }

    @Override
    public Range<T> span() {
        return delegate().span();
    }

    @Override
    public Set<Range<T>> asRanges() {
        return delegate().asRanges();
    }

    @Override
    public RangeSet<T> complement() {
        return delegate().complement();
    }

    @Override
    public RangeSet<T> subRangeSet(Range<T> view) {
        return delegate().subRangeSet(view);
    }

    @Override
    public void add(Range<T> range) {
        delegate().add(range);
    }

    @Override
    public void remove(Range<T> range) {
        delegate().remove(range);
    }

    @Override
    public void clear() {
        delegate().clear();
    }

    @Override
    public void addAll(RangeSet<T> other) {
        delegate().addAll(other);
    }

    @Override
    public void removeAll(RangeSet<T> other) {
        delegate().removeAll(other);
    }
    
}
