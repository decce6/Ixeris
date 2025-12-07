package me.decce.ixeris.core.workarounds;

import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;

public class RetinaWorkaround {
    private static final ConcurrentHashMap<Long, Integer> map = new ConcurrentHashMap<>(1);

    public static Optional<Integer> get(long window) {
        return Optional.ofNullable(map.get(window));
    }

    public static void set(long window, int retina) {
        map.put(window, retina);
    }
}
