package io.cify.common;

import io.cify.common.capability.conf.CapabilityConfig;
import org.json.JSONObject;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class CapabilityConfigTest {
    @Test
    public void testFromJson() {
        String capabilitiesJson = "{'android': [{'name': 'android1'}, {'name': 'android2'}], 'browser': [{'name': 'browser1'}], 'ios': []}";
        JSONObject capabilities = new JSONObject(capabilitiesJson);

        CapabilityConfig config = CapabilityConfig.fromJson(capabilities);

        assertEquals(2, config.getAndroidConfigs().size());
        assertEquals(1, config.getBrowserConfigs().size());
        assertEquals(0, config.getIosConfigs().size());
        assertEquals("browser1", config.getBrowserConfigs().get(0).getCapabilities().get("name"));
    }
}
