package me.decce.ixeris.util;

import java.util.NoSuchElementException;

/**
 * A synchronized boolean array FIFO queue. Implemented by wrapping {@link BooleanArrayList} since fastutil does not have BooleanArrayFIFOQueue for some reason
 */
public class SynchronizedBooleanArrayFIFOQueue {
    public static final int DEFAULT_INITIAL_CAPACITY = 4;
    private final BooleanArrayListEx queue;
    private final Object sync;
    private int start;
    private int end;

    private SynchronizedBooleanArrayFIFOQueue(BooleanArrayListEx queue) {
        this.queue = queue;
        this.sync = this;
    }

    public static SynchronizedBooleanArrayFIFOQueue synchronize(BooleanArrayListEx queue) {
        return new SynchronizedBooleanArrayFIFOQueue(queue);
    }

    public static SynchronizedBooleanArrayFIFOQueue create() {
        return synchronize(new BooleanArrayListEx(DEFAULT_INITIAL_CAPACITY));
    }

    public static SynchronizedBooleanArrayFIFOQueue create(int capacity) {
        return synchronize(new BooleanArrayListEx(capacity));
    }

    public void enqueue(boolean x) {
        synchronized(this.sync) {
            if (end >= queue.size() - 1) {
                queue.add(x);
            }
            else {
                queue.set(end, x);
            }
            if (++end == queue.capacity()) {
                end = 0;
            }
        }
    }

    public boolean dequeue() {
        synchronized(this.sync) {
            if (start == end) throw new NoSuchElementException();
            boolean t = queue.getBoolean(start++);
            if (start == queue.capacity()) {
                start = 0;
            }
            return t;
        }
    }
}
