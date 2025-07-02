package me.decce.ixeris.util;

import it.unimi.dsi.fastutil.booleans.BooleanArrayList;

public class BooleanArrayListEx extends BooleanArrayList {
    public BooleanArrayListEx() {
        super();
    }

    public BooleanArrayListEx(int capacity) {
        super(capacity);
    }

    public int capacity() {
        return super.a.length;
    }
}
