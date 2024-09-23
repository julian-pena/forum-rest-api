package com.alura.forum.model.enums;

import io.swagger.v3.oas.annotations.media.Schema;

import java.util.Arrays;
import java.util.List;

@Schema(description = "Roles available in the system, each associated with a set of permissions.")
public enum RoleEnum {

    @Schema(description = "Administrator role with full access to all permissions.")
    ADMIN(Arrays.stream(PermissionEnum.values()).toList()),

    @Schema(description = "Regular user role with limited access.")
    USER(Arrays.asList(PermissionEnum.READ_TOPIC, PermissionEnum.DELETE_TOPIC, PermissionEnum.UPDATE_TOPIC, PermissionEnum.WRITE_TOPIC,
            PermissionEnum.READ_USER, PermissionEnum.UPDATE_USER,
            PermissionEnum.READ_RESPONSE, PermissionEnum.WRITE_RESPONSE, PermissionEnum.UPDATE_RESPONSE, PermissionEnum.DELETE_RESPONSE,
            PermissionEnum.READ_COURSE)),

    @Schema(description = "Guest or invited user role with minimal access.")
    INVITED(Arrays.asList(PermissionEnum.READ_TOPIC, PermissionEnum.READ_USER, PermissionEnum.READ_COURSE, PermissionEnum.READ_RESPONSE));

    /* @Schema(description = "Developer role with specific access permissions.")
    DEVELOPER(Arrays.asList(PermissionEnum.READ_TOPIC, PermissionEnum.DELETE_TOPIC)); */
    private List<PermissionEnum> permissionEnums;

    RoleEnum(List<PermissionEnum> permissions) {
        this.permissionEnums = permissions;
    }

    public List<PermissionEnum> getPermissionEnums() {
        return permissionEnums;
    }

    public void setPermissionEnums(List<PermissionEnum> permissionEnums) {
        this.permissionEnums = permissionEnums;
    }
}