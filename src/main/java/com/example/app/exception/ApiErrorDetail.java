package com.example.app.exception;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Schema(description = "Error Detail information")
@JsonPropertyOrder({ "code","detail", "origin", "title"})
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ApiErrorDetail {

    @JsonProperty("code")
    @Schema(description = "Code")
    String code;

    @JsonProperty("detail")
    @Schema(description = "Detail")
    String detail;


    @JsonProperty("origin")
    @Schema(description = "Origin")
    String origin;

    @JsonProperty("title")
    @Schema(description = "Title")
    String title;




}
