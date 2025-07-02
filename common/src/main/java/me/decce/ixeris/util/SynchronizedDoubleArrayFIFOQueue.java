package me.decce.ixeris.util;

import it.unimi.dsi.fastutil.doubles.DoubleArrayFIFOQueue;

/**
 * Synchronized wrapped for {@link DoubleArrayFIFOQueue}, to avoid the boxing/unboxing overhead introduced by the generic wrappers from fastutil
 */
public class SynchronizedDoubleArrayFIFOQueue {
    private final DoubleArrayFIFOQueue queue;
    private final Object sync;

    private SynchronizedDoubleArrayFIFOQueue(DoubleArrayFIFOQueue queue) {
        this.queue = queue;
        this.sync = this;
    }

    public static SynchronizedDoubleArrayFIFOQueue synchronize(DoubleArrayFIFOQueue queue) {
        return new SynchronizedDoubleArrayFIFOQueue(queue);
    }

    public static SynchronizedDoubleArrayFIFOQueue create() {
        return synchronize(new DoubleArrayFIFOQueue());
    }

    public static SynchronizedDoubleArrayFIFOQueue create(int capacity) {
        return synchronize(new DoubleArrayFIFOQueue(capacity));
    }

    public void enqueue(double x) {
        synchronized(this.sync) {
            this.queue.enqueue(x);
        }
    }

    public double dequeue() {
        synchronized(this.sync) {
            return this.queue.dequeueDouble();
        }
    }
}
