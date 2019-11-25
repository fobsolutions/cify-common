package io.cify.common.capability.conf;

import io.cify.common.CifyCommonException;
import io.cify.common.DeviceCategory;
import io.cify.common.util.StringUtils;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.openqa.selenium.remote.DesiredCapabilities;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static io.cify.common.DeviceCategory.*;

public class CapabilityConfig implements ICapabilityConfig {

    private Map<DeviceCategory, List<CapabilitySet>> categorisedConfig = new HashMap<>();

    public CapabilityConfig() {
        categorisedConfig.put(BROWSER, new ArrayList<>());
        categorisedConfig.put(ANDROID, new ArrayList<>());
        categorisedConfig.put(IOS, new ArrayList<>());
    }

    public CapabilityConfig(Map<DeviceCategory, List<CapabilitySet>> categorisedConfig) {
        this.categorisedConfig = deepCopy(categorisedConfig);
    }

    /**
     * Adds {@code capabilitySet} to the specified {@code category}
     */
    @Override
    public void addCapabilitySet(DeviceCategory category, CapabilitySet capabilitySet) {
        categorisedConfig.get(category).add(capabilitySet);
    }

    @Override
    public Map<DeviceCategory, List<CapabilitySet>> getCategorisedConfig() {
        return categorisedConfig;
    }

    @Override
    public List<CapabilitySet> getBrowserConfigs() {
        return getConfigs(BROWSER);
    }

    @Override
    public List<CapabilitySet> getAndroidConfigs() {
        return getConfigs(ANDROID);
    }

    @Override
    public List<CapabilitySet> getIosConfigs() {
        return getConfigs(IOS);
    }

    @Override
    public List<CapabilitySet> getConfigs(DeviceCategory deviceCategory) {
        return categorisedConfig.get(deviceCategory);
    }

    /**
     * Generate unique string from capabilities object
     */
    @Override
    public String toString() {
        String browserCapsId = getCapabilityIdentifier(getBrowserConfigs());
        String androidCapsId = getCapabilityIdentifier(getAndroidConfigs());
        String iosCapsId = getCapabilityIdentifier(getIosConfigs());

        browserCapsId = browserCapsId.isEmpty() ? "" : "_" + browserCapsId;
        androidCapsId = androidCapsId.isEmpty() ? "" : "_" + androidCapsId;
        iosCapsId = iosCapsId.isEmpty() ? "" : "_" + iosCapsId;

        return browserCapsId + androidCapsId + iosCapsId;
    }

    @Override
    public DesiredCapabilities getDesiredCapabilities(DeviceCategory category) {
        for (CapabilitySet capabilitySet : getConfigs(category)) {
            return new DesiredCapabilities(capabilitySet.getCapabilities());
        }
        return new DesiredCapabilities();
    }

    @Override
    public DesiredCapabilities getDesiredCapabilities(String capabilityId) {
        for (DeviceCategory category : DeviceCategory.values()) {
            for (CapabilitySet capabilitySet : getConfigs(category)) {
                if (capabilitySet.getCapability(PARAM_ID).equals(capabilityId)) {
                    return new DesiredCapabilities(capabilitySet.getCapabilities());
                }
            }
        }
        return new DesiredCapabilities();
    }

    @Override
    public DeviceCategory getCategory(String capabilityId) throws CifyCommonException {
        for (DeviceCategory category : DeviceCategory.values()) {
            for (CapabilitySet capabilitySet : getConfigs(category)) {
                if (capabilitySet.getCapability(PARAM_ID).equals(capabilityId)) {
                    return category;
                }
            }
        }
        throw new CifyCommonException(String.format("Unable to get DeviceCategory of capabilityId %s", capabilityId));
    }

    @Override
    public void setCapability(String capabilityId, String key, Object value) throws CifyCommonException {
        for (DeviceCategory category : DeviceCategory.values()) {
            for (CapabilitySet capabilitySet : getConfigs(category)) {
                if (capabilitySet.getCapability(PARAM_ID) != null && capabilitySet.getCapability(PARAM_ID).equals(capabilityId)) {
                    capabilitySet.setCapability(key, value);
                }
            }
        }
        String message = String.format("Setting \"%s\":%s failed. Capability with id:%s not found", key, value, capabilityId);
        throw new CifyCommonException(message);
    }

    public static void addToAllSets(List<CapabilityConfig> capabilityConfigList, String key, String value) {
        for (CapabilityConfig capabilityConfig : capabilityConfigList) {
            capabilityConfig.addToAllSets(key, value);
        }
    }

    private void addToAllSets(String key, String value) {
        for (CapabilitySet capabilityset : categorisedConfig.get(BROWSER)) {
            capabilityset.setCapability(key, value);
        }
        for (CapabilitySet capabilityset : categorisedConfig.get(ANDROID)) {
            capabilityset.setCapability(key, value);
        }
        for (CapabilitySet capabilityset : categorisedConfig.get(IOS)) {
            capabilityset.setCapability(key, value);
        }
    }

    public static String toPrettyString(List<CapabilityConfig> capabilityConfigList) {
        StringBuilder prettyString = new StringBuilder("[");
        for (CapabilityConfig capabilityConfig : capabilityConfigList) {
            prettyString.append(capabilityConfig.toPrettyString()).append(", ");
        }
        return prettyString.substring(0, prettyString.length()-2);
    }

    /*
     * Generates class data representation string
     */
    private String toPrettyString() {
        return "[browser: " + StringUtils.toString(getBrowserConfigs()) + ", android: " + StringUtils.toString(getAndroidConfigs()) + ", ios: " + StringUtils.toString(getIosConfigs()) + "]";
    }

    /**
     * Creates an instance of CapabilityConfig from capabilities JSONObject
     * @param capabilities JSONObject, representing capabilities
     */
    public static CapabilityConfig fromJson(JSONObject capabilities) {
        JSONArray browserCapabilitySets = new JSONArray();
        JSONArray androidCapabilitySets = new JSONArray();
        JSONArray iosCapabilitySets = new JSONArray();

        try {
            browserCapabilitySets = capabilities.getJSONArray(PARAM_BROWSER);
        } catch (JSONException ignored) {
        }
        try {
            androidCapabilitySets = capabilities.getJSONArray(PARAM_ANDROID);
        } catch (JSONException ignored) {
        }
        try {
            iosCapabilitySets = capabilities.getJSONArray(PARAM_IOS);
        } catch (JSONException ignored) {
        }

        CapabilityConfig capabilityConfig = new CapabilityConfig();
        for (int i = 0; i < browserCapabilitySets.length(); i++) {
            capabilityConfig.addCapabilitySet(BROWSER, CapabilitySet.fromJson(browserCapabilitySets.getJSONObject(i)));
        }
        for (int i = 0; i < androidCapabilitySets.length(); i++) {
            capabilityConfig.addCapabilitySet(ANDROID, CapabilitySet.fromJson(androidCapabilitySets.getJSONObject(i)));
        }
        for (int i = 0; i < iosCapabilitySets.length(); i++) {
            capabilityConfig.addCapabilitySet(IOS, CapabilitySet.fromJson(iosCapabilitySets.getJSONObject(i)));
        }

        return capabilityConfig;
    }

    /**
     * Gets capabilityList identifier from LazyMap
     */
    private static String getCapabilityIdentifier(List<CapabilitySet> capabilityList) {
        if (capabilityList == null || capabilityList.isEmpty()) {
            return "";
        } else {
            StringBuilder builder = new StringBuilder();
            for (CapabilitySet capabilitySet : capabilityList) {
                Map capabilities = capabilitySet.getCapabilities();
                builder.append(capabilities.containsKey(PARAM_CAPABILITY_ID) ? "_" + capabilities.get(PARAM_CAPABILITY_ID) : "_" + capabilities.hashCode());
            }
            return builder.toString().replaceFirst("_", "");
        }
    }

    /**
     * Creates a deep copy of {@code categorizedConfig}
     */
    private Map<DeviceCategory, List<CapabilitySet>> deepCopy (Map<DeviceCategory, List<CapabilitySet>> categorizedConfig) {
        Map<DeviceCategory, List<CapabilitySet>> copy = new HashMap<>();
        for (Map.Entry<DeviceCategory, List<CapabilitySet>> entry : categorizedConfig.entrySet()) {
            List<CapabilitySet> capabilitySetList = new ArrayList<>();
            for (CapabilitySet capabilitySet : entry.getValue()) {
                capabilitySetList.add(new CapabilitySet(capabilitySet.getCapabilities()));
            }
            copy.put(entry.getKey(), capabilitySetList);
        }
        return copy;
    }
}
