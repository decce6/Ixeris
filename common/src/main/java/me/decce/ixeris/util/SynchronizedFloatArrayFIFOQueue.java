package me.decce.ixeris.util;

import it.unimi.dsi.fastutil.floats.FloatArrayFIFOQueue;

/**
 * Synchronized wrapped for {@link FloatArrayFIFOQueue}, to avoid the boxing/unboxing overhead introduced by the generic wrappers from fastutil
 */
public class SynchronizedFloatArrayFIFOQueue {
    private final FloatArrayFIFOQueue queue;
    private final Object sync;

    private SynchronizedFloatArrayFIFOQueue(FloatArrayFIFOQueue queue) {
        this.queue = queue;
        this.sync = this;
    }

    public static SynchronizedFloatArrayFIFOQueue synchronize(FloatArrayFIFOQueue queue) {
        return new SynchronizedFloatArrayFIFOQueue(queue);
    }

    public static SynchronizedFloatArrayFIFOQueue create() {
        return synchronize(new FloatArrayFIFOQueue());
    }

    public static SynchronizedFloatArrayFIFOQueue create(int capacity) {
        return synchronize(new FloatArrayFIFOQueue(capacity));
    }

    public void enqueue(float x) {
        synchronized(this.sync) {
            this.queue.enqueue(x);
        }
    }

    public float dequeue() {
        synchronized(this.sync) {
            return this.queue.dequeueFloat();
        }
    }
}
