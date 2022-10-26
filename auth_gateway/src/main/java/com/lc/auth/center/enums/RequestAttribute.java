package com.lc.auth.center.enums;


import java.util.Arrays;

public enum RequestAttribute {
    SKIP_TOKEN("Authorization", "LuCheng"),
    SKIP_AUTH("Authentication","LuCheng");

    RequestAttribute(String attribute, String whiteValue) {
        this.attribute = attribute;
        this.whiteValue = whiteValue;
    }

    public static boolean checkWhiteValue(String attribute, String whiteValue) {
        String s;
        return (s = getValueByKey(attribute)) != null && s.equals(whiteValue);
    }

    public static String getValueByKey(String attribute) {
        if (attribute != null)
            return Arrays.stream(RequestAttribute.values())
                    .filter(r -> r.attribute.equals(attribute))
                    .findFirst().map(r -> r.whiteValue)
                    .orElse(null);
        return null;
    }

    private final String attribute;
    private final String whiteValue;

    public String getAttribute() {
        return attribute;
    }

    public String getWhiteValue() {
        return whiteValue;
    }

}
