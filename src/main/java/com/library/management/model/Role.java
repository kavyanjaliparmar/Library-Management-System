package com.library.management.model;

public enum Role {
    ROLE_ADMIN("Admin"),
    ROLE_STUDENT("Student");

    private final String displayName;

    Role(String displayName) {
        this.displayName = displayName;
    }

    public String getDisplayName() {
        return displayName;
    }
}
