package io.cify.common;

import io.cify.common.capability.conf.CapabilitySet;
import org.json.JSONObject;
import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

public class CapabilitySetTest {

    @Test
    public void testFromJson() {
        String json = "{\"cap1\":\"value1\",\n\"cap2\" : \"value2\", \"cap3\": true}";
        CapabilitySet capabilitySet = CapabilitySet.fromJson(new JSONObject(json));

        assertNotNull(capabilitySet.getCapabilities());
        assertEquals(capabilitySet.getCapabilities().get("cap1"), "value1");
        assertEquals(capabilitySet.getCapabilities().get("cap2"), "value2");
        assertEquals(capabilitySet.getCapabilities().get("cap3"), true);
    }
}
