package io.cify.common.capability.conf;

import io.cify.common.DeviceCategory;

import java.util.List;
import java.util.Map;

public interface ICapabilityConfig {
    String PARAM_STRATEGY = "strategy";
    String PARAM_CAPABILITIES = "capabilities";
    String PARAM_CAPABILITY_ID = "capabilityId";
    String PARAM_FARM_URL = "remote";
    String PARAM_BROWSER = "browser";
    String PARAM_ANDROID = "android";
    String PARAM_IOS = "ios";

    void addCapabilitySet(DeviceCategory category, CapabilitySet capabilitySet);
    Map<DeviceCategory, List<CapabilitySet>> getCategorisedConfig();
    List<CapabilitySet> getBrowserConfigs();
    List<CapabilitySet> getAndroidConfigs();
    List<CapabilitySet> getIosConfigs();
}
