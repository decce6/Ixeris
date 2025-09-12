package me.decce.ixeris.util;

import net.fabricmc.loader.api.FabricLoader;

public class MappingTranslator {
    public static final String INTERMEDIARY = "intermediary";

    public static String translateField(Class<?> clazz, String fieldName, String fieldDescriptor) {
        var resolver = FabricLoader.getInstance().getMappingResolver();
        var className = resolver.unmapClassName(INTERMEDIARY, clazz.getName());
        return resolver.mapFieldName(INTERMEDIARY, className, fieldName, fieldDescriptor);
    }
}
