package io.cify.common;

import org.junit.Assert;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import static org.junit.Assert.assertEquals;

public class DeviceCategoryTest {
    @Rule
    public ExpectedException exceptionRule = ExpectedException.none();

    @Test
    public void testFromString() throws CifyCommonException {
        try {
            assertEquals(DeviceCategory.fromString("BrowseR"), DeviceCategory.BROWSER);
        } catch (CifyCommonException e) {
            e.printStackTrace();
        }

        try {
            assertEquals(DeviceCategory.fromString("AndroiD"), DeviceCategory.ANDROID);
        } catch (CifyCommonException e) {
            e.printStackTrace();
        }

        try {
            assertEquals(DeviceCategory.fromString("IoS"), DeviceCategory.IOS);
        } catch (CifyCommonException e) {
            e.printStackTrace();
        }

        exceptionRule.expect(CifyCommonException.class);
        exceptionRule.expectMessage("Error converting 'UnknownCategory' String to DeviceCategory");
        DeviceCategory.fromString("UnknownCategory");
    }
}
