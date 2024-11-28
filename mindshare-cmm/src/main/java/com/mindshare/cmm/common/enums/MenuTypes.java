package com.mindshare.cmm.common.enums;

public enum MenuTypes {

    SYSTEM("SYSTEM"),
    ADMIN("ADMIN"),
    MEMBER("MEMBER");

    private final String value;

    MenuTypes(String value) {
        this.value = value;
    }

    public String getKey() {
        return name();
    }

    public String getValue() {
        return value;
    }

}
