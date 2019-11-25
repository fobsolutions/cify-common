package io.cify.common.capability.conf;

import java.util.Map;

public interface ICapabilitySet {
    void addCapability(String key, String value);
    Map<String, String> getCapabilities();
}
