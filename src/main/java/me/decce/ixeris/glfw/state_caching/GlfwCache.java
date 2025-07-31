package me.decce.ixeris.glfw.state_caching;

import java.util.Collections;
import java.util.IdentityHashMap;
import java.util.Map;
import java.util.concurrent.atomic.AtomicInteger;

public abstract class GlfwCache {
    private static final Object PRESENT = new Object();
    
    private static final Map<Thread, Object> isRawCallingGLFW = 
            Collections.synchronizedMap(new IdentityHashMap<>());
    
    public static void markRawCalling() {
        isRawCallingGLFW.put(Thread.currentThread(), PRESENT);
    }
    
    public static void unmarkRawCalling() {
        isRawCallingGLFW.remove(Thread.currentThread());
    }
    
    public static boolean isRawCalling() {
        return isRawCallingGLFW.containsKey(Thread.currentThread());
    }
    
    protected AtomicInteger enabled;

    protected GlfwCache() {
        this.enabled = new AtomicInteger();
    }

    public boolean isCacheEnabled() {
        return enabled.get() > 0;
    }

    public void enableCache() {
        enabled.getAndIncrement();
    }

    public void disableCache() {
        enabled.getAndDecrement();
    }
}
