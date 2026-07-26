package me.decce.ixeris;

import me.decce.ixeris.core.Constants;

import java.util.List;
import java.util.stream.Collectors;

// Compiled for Java 8 for 1.16 compatibility
@SuppressWarnings("unused")
public class IxerisCoreMixinPlugin extends IxerisMixinPlugin {
    @Override
    public List<String> getMixins() {
        return Constants.getMixins().stream().filter(super::shouldApplyMixin).collect(Collectors.toList());
    }
}
