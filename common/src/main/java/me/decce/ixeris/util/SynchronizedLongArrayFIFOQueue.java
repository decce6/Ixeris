package me.decce.ixeris.util;

import it.unimi.dsi.fastutil.ints.IntArrayFIFOQueue;
import it.unimi.dsi.fastutil.longs.LongArrayFIFOQueue;

/**
 * Synchronized wrapped for {@link LongArrayFIFOQueue}, to avoid the boxing/unboxing overhead introduced by the generic wrappers from fastutil
 */
public class SynchronizedLongArrayFIFOQueue {
    private final LongArrayFIFOQueue queue;
    private final Object sync;

    private SynchronizedLongArrayFIFOQueue(LongArrayFIFOQueue queue) {
        this.queue = queue;
        this.sync = this;
    }

    public static SynchronizedLongArrayFIFOQueue synchronize(LongArrayFIFOQueue queue) {
        return new SynchronizedLongArrayFIFOQueue(queue);
    }

    public static SynchronizedLongArrayFIFOQueue create() {
        return synchronize(new LongArrayFIFOQueue());
    }

    public static SynchronizedLongArrayFIFOQueue create(int capacity) {
        return synchronize(new LongArrayFIFOQueue(capacity));
    }

    public void enqueue(long x) {
        synchronized(this.sync) {
            this.queue.enqueue(x);
        }
    }

    public long dequeue() {
        synchronized(this.sync) {
            return this.queue.dequeueLong();
        }
    }
}
