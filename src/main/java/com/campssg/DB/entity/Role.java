package com.campssg.DB.entity;

// TODO: 권한 설정 추가
public enum Role {
    ROLE_USER("ROLE_USER", "서비스 이용자"),
    ROLE_MANAGER("ROLE_MANAGER", "마트 운영자");

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
