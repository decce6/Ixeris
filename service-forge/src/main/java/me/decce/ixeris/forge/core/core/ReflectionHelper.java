package me.decce.ixeris.forge.core.core;

import sun.misc.Unsafe;

import java.lang.invoke.MethodHandle;
import java.lang.invoke.MethodHandles;
import java.lang.reflect.Field;
import java.lang.reflect.Method;

public class ReflectionHelper {
    public static final MethodHandles.Lookup LOOKUP;

    static {
        try {
            Field theUnsafe = Unsafe.class.getDeclaredField("theUnsafe");
            theUnsafe.setAccessible(true);
            Unsafe unsafe = (Unsafe) theUnsafe.get(null);
            Field implLookup = MethodHandles.Lookup.class.getDeclaredField("IMPL_LOOKUP");
            LOOKUP = (MethodHandles.Lookup) unsafe.getObject(unsafe.staticFieldBase(implLookup), unsafe.staticFieldOffset(implLookup));
        } catch (Throwable e) {
            throw new RuntimeException(e);
        }
    }

    public static MethodHandle unreflect(UncheckedSupplier<Method, ?> method) {
        try {
            return LOOKUP.unreflect(method.get());
        } catch (Throwable e) {
            throw new RuntimeException(e);
        }
    }

    public static MethodHandle unreflectGetter(UncheckedSupplier<Field, ?> field) {
        try {
            return LOOKUP.unreflectGetter(field.get());
        } catch (Throwable e) {
            throw new RuntimeException(e);
        }
    }

    @FunctionalInterface
    public interface UncheckedSupplier<T, E extends Exception> {
        T get() throws E;
    }
}
