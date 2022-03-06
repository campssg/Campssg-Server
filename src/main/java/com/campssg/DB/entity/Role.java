package com.campssg.DB.entity;

public enum Role {
    ROLE_USER("ROLE_USER", "서비스 이용자"),
    ROLE_MANAGER("ROLE_MANAGER", "마트 운영자"),
    ROLE_ADMIN("ROLE_ADMIN", "개발자");

    private final String authority;
    private final String description;

    Role(String authority, String description) {
        this.authority = authority;
        this.description = description;
    }

    public String getAuthority() {
        return authority;
    }
}
