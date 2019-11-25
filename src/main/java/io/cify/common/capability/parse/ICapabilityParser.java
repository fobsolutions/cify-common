package io.cify.common.capability.parse;

import io.cify.common.CifyCommonException;
import io.cify.common.capability.conf.CapabilityConfig;
import io.cify.common.capability.conf.ICapabilityConfig;

import java.util.List;

public interface ICapabilityParser {
    /**
     * Capabilities parsing strategy, that creates all possible variations of capabilities with one capability
     * of each kind (browser, android, ios) per {@link ICapabilityConfig} object
     */
    String STRATEGY_VARIATIONS = "variations";
    /**
     * Capabilities parsing strategy, to put all capabilities into one {@link ICapabilityConfig} object
     */
    String STRATEGY_ALL_IN_ONE = "all_in_one";
    /**
     * Capabilities parsing strategy, to put each capability into a separate {@link ICapabilityConfig} object
     */
    String STRATEGY_ONE_BY_ONE = "one_by_one";

    List<CapabilityConfig> parse(String capabilitiesConfigJson, String farmUrl) throws CifyCommonException;
    List<CapabilityConfig> parse(String capabilitiesConfigJson, String farmUrl, String strategy) throws CifyCommonException;
}
