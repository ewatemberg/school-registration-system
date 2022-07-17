package com.example.app.service.dto;

import com.example.app.utils.Messages;
import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

import java.util.Set;

@Data
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode(callSuper=true)
@SuperBuilder
@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
public class FullStudentDTO extends StudentDTO {

    private Set<CourseDTO> courseDTO;
    @Schema(description = Messages.STUDENT_TOTAL_ENROLLMENT, type = "int", example = "1")
    private int totalEnrollments;

    public FullStudentDTO(Long id, String firstName, String lastName, String email, Set<CourseDTO> courses, int totalEnrollment) {
        super(id, firstName, lastName, email);
        this.courseDTO = courses;
        this.totalEnrollments = totalEnrollment;
    }
}
