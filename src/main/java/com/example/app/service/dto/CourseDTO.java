package com.example.app.service.dto;

import com.example.app.utils.Messages;
import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;
import org.springframework.beans.factory.annotation.Value;

import javax.validation.constraints.NotNull;

@Data
@AllArgsConstructor
@NoArgsConstructor
@SuperBuilder
@EqualsAndHashCode
@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
@Schema
public class CourseDTO {

    private Long id;
    @Schema(description = Messages.COURSE_NAME, type = "string", example = "Programming")
    @NotNull
    private String name;
    @Value("${course.max.capacity:50}")
    @Schema(description = Messages.COURSE_MAXIMUM_CAPACITY, type = "int", example = "50")
    private int maximumCapacity;
    @Schema(description = Messages.COURSE_REGISTRATION_AVAILABLE, type = "int", example = "50")
    private int registrationAvailable;

}
