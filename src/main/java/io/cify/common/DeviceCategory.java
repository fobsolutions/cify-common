package io.cify.common;

public enum DeviceCategory {
    BROWSER,
    ANDROID,
    IOS;

    public static DeviceCategory fromString(String category) throws CifyCommonException {
        try {
            return DeviceCategory.valueOf(category.toUpperCase());
        } catch (IllegalArgumentException e) {
            throw new CifyCommonException(String.format("Error converting '%s' String to DeviceCategory", category), e);
        }
    }
}
