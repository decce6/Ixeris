package me.decce.ixeris.threading;

import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.function.Supplier;

import com.google.common.collect.Queues;

import me.decce.ixeris.BlockingException;
import me.decce.ixeris.Ixeris;

public class MainThreadDispatcher {
    private static final ConcurrentLinkedQueue<Runnable> blockingTaskQueue = Queues.newConcurrentLinkedQueue();
    private static final ConcurrentLinkedQueue<Runnable> mainThreadRecordingQueue = Queues.newConcurrentLinkedQueue();
    
    private static final Object mainThreadLock = new Object();

    public static boolean isOnThread() {
        return Ixeris.isOnMainThread();
    }

    public static <T> T query(Supplier<T> supplier) {
        if (isOnThread()) {
            return supplier.get();
        }
        if (Ixeris.getConfig().shouldLogBlockingCalls()) {
            Ixeris.LOGGER.warn("A GLFW call has been made on non-main thread. This might lead to reduced performance.", new BlockingException());
        }
        Query<T> query = new Query<>(supplier);
        synchronized (mainThreadLock) {
            blockingTaskQueue.add(query);
            mainThreadLock.notify();
		}
        while (!query.hasFinished) {
            Thread.onSpinWait();
        }
        return query.result;
    }

    public static void run(Runnable runnable) {
        if (Ixeris.getConfig().isFullyBlockingMode()) {
            runNow(runnable);
        } else {
            runLater(runnable);
        }
    }

    public static void runLater(Runnable runnable) {
        mainThreadRecordingQueue.add(runnable);
    }

    public static void runNow(Runnable runnable) {
        if (isOnThread()) {
            runnable.run();
            return;
        }
        if (Ixeris.getConfig().shouldLogBlockingCalls()) {
            Ixeris.LOGGER.warn("A GLFW call has been made on non-main thread. This might lead to reduced performance.", new BlockingException());
        }
        ImmediateRunnable runnableWrapper = new ImmediateRunnable(runnable);
        synchronized (mainThreadLock) {
            blockingTaskQueue.add(runnableWrapper);
            mainThreadLock.notify();
		}
        while (!runnableWrapper.hasFinished) {
            Thread.onSpinWait();
        }
    }

    public static void replayQueue() {
    	while (true) {
    		Runnable nextTask;
    		synchronized (mainThreadLock) {
				if((nextTask = blockingTaskQueue.poll()) == null &&
						(nextTask = mainThreadRecordingQueue.poll()) == null) {
					if(!Ixeris.getConfig().isGreedyEventPolling()) {
						await(200L);
					}else {
						await(4L);
					}
					break;
				}
			}
    		nextTask.run();
		}
    }
    
    public static void awake() {
    	synchronized (mainThreadLock) {
    		mainThreadLock.notify();
		}
    }
    
    public static void await(long timeout) {
    	try {
			mainThreadLock.wait(timeout);
		} catch (InterruptedException ignored) {}
    }
    
    private static class Query<T> implements Runnable {
    	private final Supplier<T> query;
    	private volatile T result = null;
    	private volatile boolean hasFinished = false;
    	
    	public Query(Supplier<T> query) {
			this.query = query;
		}
    	
    	@Override
    	public void run() {
    		result = query.get();
    		hasFinished = true;
    	}
    }
    
    private static class ImmediateRunnable implements Runnable {
    	private final Runnable runnable;
    	private volatile boolean hasFinished = false;
    	
    	public ImmediateRunnable(Runnable runnable) {
			this.runnable = runnable;
		}

		@Override
		public void run() {
			runnable.run();
			hasFinished = true;
		}
    }
}
