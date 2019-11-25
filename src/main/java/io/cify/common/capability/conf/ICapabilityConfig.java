package io.cify.common.capability.conf;

import io.cify.common.CifyCommonException;
import io.cify.common.DeviceCategory;
import org.openqa.selenium.remote.DesiredCapabilities;

import java.util.List;
import java.util.Map;

public interface ICapabilityConfig {
    String PARAM_STRATEGY = "strategy";
    String PARAM_CAPABILITIES = "capabilities";
    String PARAM_CAPABILITY_ID = "capabilityId";
    String PARAM_ID = "id";
    String PARAM_FARM_URL = "remote";
    String PARAM_BROWSER = "browser";
    String PARAM_ANDROID = "android";
    String PARAM_IOS = "ios";
    String PARAM_AVAILABLE = "available";

    void addCapabilitySet(DeviceCategory category, CapabilitySet capabilitySet);
    Map<DeviceCategory, List<CapabilitySet>> getCategorisedConfig();
    List<CapabilitySet> getBrowserConfigs();
    List<CapabilitySet> getAndroidConfigs();
    List<CapabilitySet> getIosConfigs();
    List<CapabilitySet> getConfigs(DeviceCategory deviceCategory);

    void setCapability(String capabilityId, String key, Object value) throws CifyCommonException;
    DesiredCapabilities getDesiredCapabilities(DeviceCategory category);
    DesiredCapabilities getDesiredCapabilities(String capabilityId);
    DeviceCategory getCategory(String capabilityId) throws CifyCommonException;
}
