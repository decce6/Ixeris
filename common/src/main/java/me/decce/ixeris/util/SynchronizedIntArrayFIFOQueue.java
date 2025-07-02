package me.decce.ixeris.util;

import it.unimi.dsi.fastutil.ints.IntArrayFIFOQueue;

/**
 * Synchronized wrapped for {@link IntArrayFIFOQueue}, to avoid the boxing/unboxing overhead introduced by the generic wrappers from fastutil
 */
public class SynchronizedIntArrayFIFOQueue {
    private final IntArrayFIFOQueue queue;
    private final Object sync;

    private SynchronizedIntArrayFIFOQueue(IntArrayFIFOQueue queue) {
        this.queue = queue;
        this.sync = this;
    }

    public static SynchronizedIntArrayFIFOQueue synchronize(IntArrayFIFOQueue queue) {
        return new SynchronizedIntArrayFIFOQueue(queue);
    }

    public static SynchronizedIntArrayFIFOQueue create() {
        return synchronize(new IntArrayFIFOQueue());
    }

    public static SynchronizedIntArrayFIFOQueue create(int capacity) {
        return synchronize(new IntArrayFIFOQueue(capacity));
    }

    public void enqueue(int x) {
        synchronized(this.sync) {
            this.queue.enqueue(x);
        }
    }

    public int dequeue() {
        synchronized(this.sync) {
            return this.queue.dequeueInt();
        }
    }
}
