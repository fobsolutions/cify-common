package io.cify.common.capability.conf;

import io.cify.common.util.StringUtils;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class CapabilitySet implements ICapabilitySet {
    private Map<String, Object> capabilities;

    CapabilitySet(Map<String, Object> capabilities) {
        this.capabilities = new HashMap<>(capabilities);
    }

    /**
     * Adds capability to the set
     * @param key key of a capability to add
     * @param value value of a capability to add
     */
    @Override
    public void setCapability(String key, Object value) {
        this.capabilities.put(key, value);
    }

    @Override
    public Map<String, Object> getCapabilities() {
        return capabilities;
    }

    @Override
    public Object getCapability(String capabilityKey) {
        return capabilities.get(capabilityKey);
    }

    @Override
    public String toString() {
        return StringUtils.toString(capabilities);
    }

    public static CapabilitySet fromJson(JSONObject capabilitySet) {
        Map<String, Object> capabilities = new HashMap<>(capabilitySet.length());
        for (String key : capabilitySet.keySet()) {
            capabilities.put(key, capabilitySet.get(key));
        }
        return new CapabilitySet(capabilities);
    }
}
