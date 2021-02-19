package me.hjjang.securityexample.service;

public enum Role {

    ADMIN("ROLE_ADMIN"),
    MEMBER("ROLE_MEMBER");

    private String value;

    Role(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }
}
