package io.cify.common;

import io.cify.common.capability.conf.CapabilityConfig;
import io.cify.common.capability.parse.CapabilityParser;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import java.util.List;

import static org.junit.Assert.assertEquals;

public class CapabilityParserTest {
    @Rule
    public ExpectedException exceptionRule = ExpectedException.none();

    @Test
    public void testParse() throws CifyCommonException {
        String capabilityConfigJson = getCapabilitiesStr("");
        try {
            List<CapabilityConfig> parsedConfig = new CapabilityParser().parse(capabilityConfigJson, "");
            assertEquals(8, parsedConfig.size());
        } catch (CifyCommonException e) {
            e.printStackTrace();
        }

        capabilityConfigJson = getCapabilitiesStr(CapabilityParser.STRATEGY_VARIATIONS);
        try {
            List<CapabilityConfig> parsedConfig = new CapabilityParser().parse(capabilityConfigJson, "");
            assertEquals(8, parsedConfig.size());

            for (CapabilityConfig capabilityConfig : parsedConfig) {
                int capabilitySetsCount = capabilityConfig.getBrowserConfigs().size()
                        + capabilityConfig.getAndroidConfigs().size()
                        + capabilityConfig.getIosConfigs().size();
                assertEquals(3, capabilitySetsCount);
            }
        } catch (CifyCommonException e) {
            e.printStackTrace();
        }

        capabilityConfigJson = getCapabilitiesStr(CapabilityParser.STRATEGY_ONE_BY_ONE);
        try {
            List<CapabilityConfig> parsedConfig = new CapabilityParser().parse(capabilityConfigJson, "");
            assertEquals(7, parsedConfig.size());

            for (CapabilityConfig capabilityConfig : parsedConfig) {
                int capabilitySetsCount = capabilityConfig.getBrowserConfigs().size()
                        + capabilityConfig.getAndroidConfigs().size()
                        + capabilityConfig.getIosConfigs().size();
                assertEquals(1, capabilitySetsCount);
            }
        } catch (CifyCommonException e) {
            e.printStackTrace();
        }

        capabilityConfigJson = getCapabilitiesStr(CapabilityParser.STRATEGY_ALL_IN_ONE);
        try {
            List<CapabilityConfig> parsedConfig = new CapabilityParser().parse(capabilityConfigJson, "");
            assertEquals(1, parsedConfig.size());

            CapabilityConfig capabilityConfig = parsedConfig.get(0);
            int capabilitySetsCount = capabilityConfig.getBrowserConfigs().size()
                    + capabilityConfig.getAndroidConfigs().size()
                    + capabilityConfig.getIosConfigs().size();
            assertEquals(7, capabilitySetsCount);
        } catch (CifyCommonException e) {
            e.printStackTrace();
        }

        capabilityConfigJson = "{}";
        exceptionRule.expect(CifyCommonException.class);
        exceptionRule.expectMessage("Cannot find 'capabilities' in json configuration file.");
        new CapabilityParser().parse(capabilityConfigJson, "");
    }


    private static String getCapabilitiesStr(String strategy) {
        if (!strategy.isEmpty()) {
            strategy = String.format(" \"strategy\": \"%s\",", strategy);
        }
        String content = "  \"capabilities\": {" +
                "    \"browser\": [" +
                "      {" +
                "        \"version\": \"48\"," +
                "        \"capability\": \"chrome\"" +
                "      }," +
                "      {" +
                "        \"version\": \"77\"," +
                "        \"capability\": \"firefox\"" +
                "      }," +
                "      {" +
                "        \"version\": \"44\"," +
                "        \"capability\": \"safari\"" +
                "      }," +
                "      {" +
                "        \"version\": \"6.0\"," +
                "        \"capability\": \"android\"" +
                "      }" +
                "    ]," +
                "    \"ios\": [" +
                "      {" +
                "        \"version\": \"9.3.5\"," +
                "        \"capability\": \"ipad\"" +
                "      }," +
                "      {" +
                "        \"version\": \"10.0.0\"," +
                "        \"capability\": \"iphone\"" +
                "      }" +
                "    ]," +
                "    \"android\": [" +
                "      {" +
                "        \"version\": \"6.0\"," +
                "        \"capability\": \"android\"" +
                "      }" +
                "    ]" +
                "  }";
        return "{" +
                strategy +
                content +
        "}";
    }
}
