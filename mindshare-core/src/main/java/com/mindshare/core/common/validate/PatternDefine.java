package com.mindshare.core.common.validate;

public class PatternDefine {

    private PatternDefine() {
        throw new IllegalStateException("Utility class");
    }

    public static final String PASSWORD_NUMBER_PATTERN = "^(?=.*[A-Za-z])(?=.*\\d)[A-Za-z\\d`~!@#$%^&*()\\-_=+]{8,20}$"; //영문, 숫자 필수 포함

}
