package com.alura.forum.model.enums;

import java.util.Arrays;
import java.util.List;
import java.util.Set;

public enum RoleEnum {

    ADMIN(Arrays.asList(PermissionEnum.READ_TOPIC, PermissionEnum.DELETE_TOPIC)),
    USER(Arrays.asList(PermissionEnum.READ_TOPIC, PermissionEnum.DELETE_TOPIC)),
    INVITED(Arrays.asList(PermissionEnum.READ_TOPIC, PermissionEnum.DELETE_TOPIC)),
    DEVELOPER(Arrays.asList(PermissionEnum.READ_TOPIC, PermissionEnum.DELETE_TOPIC));

    private List<PermissionEnum> permissionEnums;

    RoleEnum(List<PermissionEnum> permissions){
        this.permissionEnums = permissions;
    }


    public List<PermissionEnum> getPermissionEnums() {
        return permissionEnums;
    }

    public void setPermissionEnums(List<PermissionEnum> permissionEnums) {
        this.permissionEnums = permissionEnums;
    }
}
