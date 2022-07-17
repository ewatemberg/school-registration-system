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

import javax.validation.constraints.Email;
import javax.validation.constraints.NotNull;

@Data
@AllArgsConstructor
@NoArgsConstructor
@SuperBuilder
@EqualsAndHashCode
@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
@Schema
public class StudentDTO {
    private Long id;
    @NotNull
    @Schema(description = Messages.STUDENT_FIRSTNAME, type = "string", example = "John")
    private String firstName;
    @NotNull
    @Schema(description = Messages.STUDENT_LASTNAME, type = "string", example = "Wick")
    private String lastName;
    @Email(regexp = "^([a-zA-Z0-9_\\-\\.]+)@([a-zA-Z0-9_\\-\\.]+)\\.([a-zA-Z]{2,5})$")
    @Schema(description = Messages.STUDENT_EMAIL, type = "string", example = "johnwick@domain.com")
    private String email;
}
