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
    DELETE_TOPIC,

    @Schema(description = "Permission to read user information.")
    READ_USER,

    @Schema(description = "Permission to create or write new user information.")
    WRITE_USER,

    @Schema(description = "Permission to update user information.")
    UPDATE_USER,

    @Schema(description = "Permission to delete users.")
    DELETE_USER,

    @Schema(description = "Permission to read responses.")
    READ_RESPONSE,

    @Schema(description = "Permission to write or create new responses.")
    WRITE_RESPONSE,

    @Schema(description = "Permission to update responses.")
    UPDATE_RESPONSE,

    @Schema(description = "Permission to delete responses.")
    DELETE_RESPONSE,

    @Schema(description = "Permission to read courses.")
    READ_COURSE,

    @Schema(description = "Permission to write or create new courses.")
    WRITE_COURSE,

    @Schema(description = "Permission to update courses.")
    UPDATE_COURSE,

    @Schema(description = "Permission to delete courses.")
    DELETE_COURSE
}
