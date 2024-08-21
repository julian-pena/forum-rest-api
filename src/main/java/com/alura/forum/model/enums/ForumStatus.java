package com.alura.forum.model.enums;

import io.swagger.v3.oas.annotations.media.Schema;

@Schema(description = "Status of a forum topic.")
public enum ForumStatus {

    @Schema(description = "The topic is open for discussion.")
    OPEN,

    @Schema(description = "The topic is closed and no longer accepting responses.")
    CLOSED,

    @Schema(description = "The topic is pending review or action.")
    PENDING,

    @Schema(description = "The topic has been resolved.")
    RESOLVED,

    @Schema(description = "The topic is unresolved.")
    UNRESOLVED,

    @Schema(description = "The topic has been archived.")
    ARCHIVED,

    @Schema(description = "The topic has been deleted.")
    DELETED,

    @Schema(description = "The topic is under review.")
    UNDER_REVIEW,

    @Schema(description = "The topic has been marked as spam.")
    SPAM,

    @Schema(description = "The topic has been locked and cannot be edited or responded to.")
    LOCKED
}


