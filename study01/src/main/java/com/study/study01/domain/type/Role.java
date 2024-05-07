package com.study.study01.domain.type;

public enum Role {
    ADMIN,
    USER;

    private static final String PREFIX = "ROLE_";

    public String getSecurityRole() {
        return PREFIX + name();
    }
}
