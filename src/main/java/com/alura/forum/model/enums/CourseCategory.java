package com.alura.forum.model.enums;

import io.swagger.v3.oas.annotations.media.Schema;

@Schema(description = "Categories for courses available in the system.")
public enum CourseCategory {

    @Schema(description = "Back-end development courses.")
    BACK_END_DEVELOPMENT,

    @Schema(description = "Front-end development courses.")
    FRONT_END_DEVELOPMENT,

    @Schema(description = "Data science courses.")
    DATA_SCIENCE,

    @Schema(description = "Machine learning courses.")
    MACHINE_LEARNING,

    @Schema(description = "Database management courses.")
    DATABASES,

    @Schema(description = "Cloud computing courses.")
    CLOUD_COMPUTING,

    @Schema(description = "DevOps courses.")
    DEVOPS,

    @Schema(description = "Cybersecurity courses.")
    CYBER_SECURITY,

    @Schema(description = "Project management courses.")
    PROJECT_MANAGEMENT,

    @Schema(description = "Mobile development courses.")
    MOBILE_DEVELOPMENT
}
