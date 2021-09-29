package com.elvis.springapp.dukachallenge.security;

public enum ApplicationSecurityPermissions {
    TASK_READ("task:read"), TASK_WRITE("task:write");

    private String permission;
    ApplicationSecurityPermissions(String permission) {
        this.permission=permission;
    }

    public String getPermission() {
        return permission;
    }
}
