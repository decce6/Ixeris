package me.decce.ixeris.threading;

import com.google.common.collect.Queues;
import java.util.concurrent.ConcurrentLinkedQueue;

public class RenderThreadDispatcher {
    private static final ConcurrentLinkedQueue<Runnable> recordingQueue = Queues.newConcurrentLinkedQueue();

    public static void runLater(Runnable runnable) {
        recordingQueue.add(runnable);
    }

    public static void replayQueue() {
        while (!recordingQueue.isEmpty()) {
            recordingQueue.poll().run();
        }
    }
}
