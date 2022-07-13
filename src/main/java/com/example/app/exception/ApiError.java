package com.example.app.exception;

import com.example.app.enums.ErrorCode;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import com.example.app.enums.ErrorType;

import java.util.ArrayList;
import java.util.List;

@Schema(description = "Error information")
@JsonPropertyOrder({ "type", "code", "detail", "error"})
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ApiError {

    @JsonProperty("type")
    @Schema(description = "Define the types of errors", allowableValues = {"BUSSINES", "APPLICATION"})
    ErrorType type;

    @JsonProperty("code")
    @Schema(description = "Error Code")
    ErrorCode code;

    @JsonProperty("detail")
    @Schema(description = "General Description")
    String detail;

    @JsonProperty("error")
    @Schema(description = "Stack Error")
    List<ApiErrorDetail> errorDetailList = new ArrayList<>();

    public ApiError(ErrorType errorType, String message) {
        this.type = errorType;
        this.detail = message;
        this.code = ErrorCode.NO_CODE;
    }

    public ApiError(ErrorType errorType, ErrorCode errorCode, String message) {
        this.type = errorType;
        this.detail = message;
        this.code = errorCode;
    }

}
