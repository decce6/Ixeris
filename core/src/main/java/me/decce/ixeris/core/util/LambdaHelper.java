package me.decce.ixeris.core.util;

import java.util.function.*;

public class LambdaHelper {
    public static Runnable makeRunnable(Runnable runnable) {
        return runnable;
    }

    public static <T> Runnable makeRunnable(Consumer<T> con, T arg0) {
        return () -> con.accept(arg0);
    }

    public static <T0, T1> Runnable makeRunnable(BiConsumer<T0, T1> con, T0 arg0, T1 arg1) {
        return () -> con.accept(arg0, arg1);
    }

    public static <T0, T1, T2> Runnable makeRunnable(TriConsumer<T0, T1, T2> con, T0 arg0, T1 arg1, T2 arg2) {
        return () -> con.accept(arg0, arg1, arg2);
    }

    public static <T0, T1, T2, T3, T4> Runnable makeRunnable(Consumer5<T0, T1, T2, T3, T4> con, T0 arg0, T1 arg1, T2 arg2, T3 arg3, T4 arg4) {
        return () -> con.accept(arg0, arg1, arg2, arg3, arg4);
    }

    public static <T0, T1, T2, T3, T4, T5, T6> Runnable makeRunnable(Consumer7<T0, T1, T2, T3, T4, T5, T6> con, T0 arg0, T1 arg1, T2 arg2, T3 arg3, T4 arg4, T5 arg5, T6 arg6) {
        return () -> con.accept(arg0, arg1, arg2, arg3, arg4, arg5, arg6);
    }

    public static <T> Supplier<T> makeSupplier(Supplier<T> supplier) {
        return supplier;
    }

    public static <T, R> Supplier<R> makeSupplier(Function<T, R> func, T arg0) {
        return () -> func.apply(arg0);
    }

    public static <T0, T1, R> Supplier<R> makeSupplier(BiFunction<T0, T1, R> func, T0 arg0, T1 arg1) {
        return () -> func.apply(arg0, arg1);
    }

    public static <T0, T1, T2, R> Supplier<R> makeSupplier(TriFunction<T0, T1, T2, R> func, T0 arg0, T1 arg1, T2 arg2) {
        return () -> func.apply(arg0, arg1, arg2);
    }

    public static <T0, T1, T2, T3, T4, R> Supplier<R> makeSupplier(Function5<T0, T1, T2, T3, T4, R> func, T0 arg0, T1 arg1, T2 arg2, T3 arg3, T4 arg4) {
        return () -> func.apply(arg0, arg1, arg2, arg3, arg4);
    }

    @FunctionalInterface
    public interface TriConsumer<T0, T1, T2> {
        void accept(T0 arg0, T1 arg1, T2 arg2);
    }

    @FunctionalInterface
    public interface Consumer7<T0, T1, T2, T3, T4, T5, T6> {
        void accept(T0 arg0, T1 arg1, T2 arg2, T3 arg3, T4 arg4, T5 arg5, T6 arg6);
    }

    @FunctionalInterface
    public interface Consumer5<T0, T1, T2, T3, T4> {
        void accept(T0 arg0, T1 arg1, T2 arg2, T3 arg3, T4 arg4);
    }

    @FunctionalInterface
    public interface TriFunction<T0, T1, T2, R> {
        R apply(T0 arg0, T1 arg1, T2 arg2);
    }

    @FunctionalInterface
    public interface Function5<T0, T1, T2, T3, T4, R> {
        R apply(T0 arg0, T1 arg1, T2 arg2, T3 arg3, T4 arg4);
    }
}