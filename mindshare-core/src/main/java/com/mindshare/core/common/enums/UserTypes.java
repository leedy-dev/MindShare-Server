package com.mindshare.core.common.enums;

public enum UserTypes {

    MEMBER("MEMBER"),
    ADMIN("ADMIN");

    private final String value;

    UserTypes(String value) {
        this.value = value;
    }

    public String getKey() {
        return name();
    }

    public String getValue() {
        return value;
    }

}
