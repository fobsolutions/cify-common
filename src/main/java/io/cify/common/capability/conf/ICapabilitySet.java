package io.cify.common.capability.conf;

import java.util.Map;

public interface ICapabilitySet {
    void setCapability(String key, Object value);
    Map<String, Object> getCapabilities();
    Object getCapability(String capabilityKey);
}
