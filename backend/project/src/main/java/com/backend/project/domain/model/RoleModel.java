package com.backend.project.domain.model;

import java.util.UUID;

public class RoleModel {

    private UUID roleId;
    private Role name;
    private String description;

    public RoleModel(UUID roleId, Role name, String description) {
        this.roleId = roleId;
        this.name = name;
        this.description = description;
    }

    public UUID getRoleId() {
        return roleId;
    }

    public void setRoleId(UUID roleId) {
        this.roleId = roleId;
    }

    public Role getName() {
        return name;
    }

    public void setName(Role name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
