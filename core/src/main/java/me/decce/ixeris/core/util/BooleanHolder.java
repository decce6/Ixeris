package me.decce.ixeris.core.util;

public record BooleanHolder(boolean value) {
    public static BooleanHolder of(boolean value) {
        return new BooleanHolder(value);
    }
}
