package io.cify.common.capability.parse;

import io.cify.common.CifyCommonException;
import io.cify.common.DeviceCategory;
import io.cify.common.capability.conf.CapabilityConfig;
import io.cify.common.capability.conf.CapabilitySet;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import static io.cify.common.capability.conf.CapabilityConfig.*;

public class CapabilityParser implements ICapabilityParser {
    private static final Logger LOG = LogManager.getLogger(CapabilityParser.class);

    /**
     * Parses Cify configuration file content
     * @return A list of CapabilityConfig objects
     */
    @Override
    public List<CapabilityConfig> parse(String cifyConfigJson, String farmUrl) throws CifyCommonException {
        if (cifyConfigJson == null) {
            throw new CifyCommonException("CapabilityConfig json is null. Nothing to parse.");
        }

        JSONObject capabilitiesConfig = new JSONObject(cifyConfigJson);
        String strategy = getString(capabilitiesConfig, PARAM_STRATEGY, STRATEGY_VARIATIONS);

        JSONObject capabilities;
        try {
            capabilities = capabilitiesConfig.getJSONObject(PARAM_CAPABILITIES);
        } catch (JSONException e) {
            throw new CifyCommonException("Cannot find 'capabilities' in json configuration file.", e);
        }

        CapabilityConfig initialConfig = CapabilityConfig.fromJson(capabilities);

        List<CapabilityConfig> parsedConfig = applyStrategy(initialConfig, strategy);

        if (farmUrl != null && !farmUrl.isEmpty()) {
            CapabilityConfig.addToAllSets(parsedConfig, PARAM_FARM_URL, farmUrl);
        }

        LOG.debug("Parsed capabilities list is: {}", toPrettyString(parsedConfig));
        return parsedConfig;
    }

    private List<CapabilityConfig> applyStrategy(CapabilityConfig initialConfig, String strategy) {
        LOG.debug("Parsing capabilities with strategy: " + strategy);
        switch (strategy) {
            case STRATEGY_ONE_BY_ONE:
                return getCapabilitiesOneByOne(initialConfig);
            case STRATEGY_ALL_IN_ONE:
                return getCapabilitiesAllInOne(initialConfig);
            default:
                return getCapabilityVariations(initialConfig);
        }
    }

    private String getString(JSONObject json, String key, String defaultValue) {
        try {
            return json.getString(key);
        } catch (JSONException ignored) {
            return defaultValue;
        }
    }

    private List<CapabilityConfig> getCapabilityVariations(CapabilityConfig initialConfig) {
        List<CapabilityConfig> variations = new ArrayList<>();
        variations.add(new CapabilityConfig());

        Map<DeviceCategory, List<CapabilitySet>> categorisedConfig = initialConfig.getCategorisedConfig();
        for (Entry<DeviceCategory, List<CapabilitySet>> entry : categorisedConfig.entrySet()) {
            variations = vary(variations, entry.getValue(), entry.getKey());
        }
        return variations;
    }

    private List<CapabilityConfig> vary(List<CapabilityConfig> capabilityConfigList,
                                        List<CapabilitySet> capabilitySetList,
                                        DeviceCategory category) {
        List<CapabilityConfig> resultList = new ArrayList<>();
        for (CapabilityConfig capabilityConfig : capabilityConfigList) {
            for (CapabilitySet capabilitySet : capabilitySetList) {
                CapabilityConfig newConfig = new CapabilityConfig(capabilityConfig.getCategorisedConfig());
                newConfig.addCapabilitySet(category, capabilitySet);
                resultList.add(newConfig);
            }
        }
        return resultList;
    }

    private List<CapabilityConfig> getCapabilitiesOneByOne(CapabilityConfig initialConfig) {
        List<CapabilityConfig> oneByOne = new ArrayList<>();
        Map<DeviceCategory, List<CapabilitySet>> categorisedConfig = initialConfig.getCategorisedConfig();
        for (Entry<DeviceCategory, List<CapabilitySet>> entry : categorisedConfig.entrySet()) {
            for (CapabilitySet capabilitySet : entry.getValue()) {
                CapabilityConfig capabilityConfig = new CapabilityConfig();
                capabilityConfig.addCapabilitySet(entry.getKey(), capabilitySet);
                oneByOne.add(capabilityConfig);
            }
        }
        return oneByOne;
    }

    private List<CapabilityConfig> getCapabilitiesAllInOne(CapabilityConfig initialConfig) {
        List<CapabilityConfig> allInOne = new ArrayList<>();
        allInOne.add(initialConfig);
        return allInOne;
    }
}