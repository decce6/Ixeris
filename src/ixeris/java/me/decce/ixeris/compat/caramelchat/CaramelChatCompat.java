package me.decce.ixeris.compat.caramelchat;

import com.mojang.blaze3d.systems.RenderSystem;
import me.decce.ixeris.core.threading.MainThreadDispatcher;
import me.decce.ixeris.core.threading.RenderThreadDispatcher;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

/**
 * Keeps caramelChat's CocoaInput calls on the thread that owns the native window while Ixeris is enabled.
 *
 * <p>The bridge deliberately uses reflection so caramelChat remains an optional dependency.</p>
 */
public final class CaramelChatCompat {
    private static final ConcurrentMap<MethodKey, Method> METHODS = new ConcurrentHashMap<>();

    private CaramelChatCompat() {
    }

    public static boolean isOnRenderThread() {
        return RenderSystem.isOnRenderThread();
    }

    public static void invokeOnMainThread(Object target, String methodName, Class<?> parameterType, Object argument) {
        MainThreadDispatcher.runNow(() -> invoke(target, methodName, new Class<?>[]{parameterType}, argument));
    }

    public static Object queryOnMainThread(Object target, String methodName) {
        return MainThreadDispatcher.query(() -> invoke(target, methodName, new Class<?>[0]));
    }

    public static void invokeLaterOnRenderThread(Object target, String methodName, Class<?> parameterType, Object argument) {
        RenderThreadDispatcher.runLater(() -> invoke(target, methodName, new Class<?>[]{parameterType}, argument));
    }

    private static Object invoke(Object target, String methodName, Class<?>[] parameterTypes, Object... arguments) {
        try {
            return cachedMethod(target.getClass(), methodName, parameterTypes).invoke(target, arguments);
        } catch (IllegalAccessException e) {
            throw new IllegalStateException("Unable to access caramelChat compatibility target", e);
        } catch (InvocationTargetException e) {
            throw propagate(e.getCause());
        }
    }

    private static Method cachedMethod(Class<?> owner, String name, Class<?>[] parameterTypes) {
        MethodKey key = new MethodKey(owner, name, parameterTypes);
        return METHODS.computeIfAbsent(key, ignored -> {
            try {
                return owner.getMethod(name, parameterTypes);
            } catch (NoSuchMethodException e) {
                throw new IllegalStateException("Unsupported caramelChat method: " + owner.getName() + '#' + name, e);
            }
        });
    }

    private static RuntimeException propagate(Throwable throwable) {
        if (throwable instanceof RuntimeException runtimeException) {
            return runtimeException;
        }
        if (throwable instanceof Error error) {
            throw error;
        }
        return new RuntimeException(throwable);
    }

    private record MethodKey(Class<?> owner, String name, Class<?>[] parameterTypes) {
        @Override
        public boolean equals(Object other) {
            return other instanceof MethodKey key
                    && owner.equals(key.owner)
                    && name.equals(key.name)
                    && Arrays.equals(parameterTypes, key.parameterTypes);
        }

        @Override
        public int hashCode() {
            return 31 * (31 * owner.hashCode() + name.hashCode()) + Arrays.hashCode(parameterTypes);
        }
    }
}
