package com.alura.forum.model.enums;

import io.swagger.v3.oas.annotations.media.Schema;

@Schema(description = "Permissions available for users in the system.")
public enum PermissionEnum {

    @Schema(description = "Permission to read topics.")
    READ_TOPIC,

    @Schema(description = "Permission to write or create new topics.")
    WRITE_TOPIC,

    @Schema(description = "Permission to update existing topics.")
    UPDATE_TOPIC,

    @Schema(description = "Permission to delete topics.")
    DELETE_TOPIC
}