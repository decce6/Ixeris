/*
 * Copyright (C) decce
 * SPDX-License-Identifier: 0BSD
 */

package me.decce.ixeris.api;

import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;

public class IxerisFuture<T> implements Future<T> {
    private volatile boolean done;
    private volatile T value;

    @Override
    public boolean cancel(boolean mayInterruptIfRunning) {
        return false;
    }

    @Override
    public boolean isCancelled() {
        return false;
    }

    @Override
    public boolean isDone() {
        return done;
    }

    @Override
    public T get() {
        while (!isDone()) {
            Thread.onSpinWait();
        }
        return value;
    }

    @Override
    public T get(long timeout, TimeUnit unit) throws TimeoutException {
        long target = System.currentTimeMillis() + TimeUnit.MILLISECONDS.convert(timeout, unit);
        while (!isDone()) {
            if (System.currentTimeMillis() > target) {
                throw new TimeoutException();
            }
            Thread.yield();
        }
        return value;
    }

    void set(T value) {
        this.value = value;
        this.done = true;
    }
}
