package me.decce.ixeris.core.util;

public class VolatileObjectHolder<T> {
    private volatile T value;

    public VolatileObjectHolder(T value) {
        this.value = value;
    }

    public void setValue(T value) {
        this.value = value;
    }

    public T getValue() {
        return value;
    }

    public static <T> VolatileObjectHolder<T> of(T value) {
        return new VolatileObjectHolder<>(value);
    }
}
