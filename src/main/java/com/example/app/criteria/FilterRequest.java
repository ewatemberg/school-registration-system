package com.example.app.criteria;

import com.example.app.utils.Messages;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
@Schema
public class FilterRequest implements Serializable {

    private static final long serialVersionUID = 6293344849078612450L;
    @Schema(description = Messages.FILTER_KEY, type = "string", example = "name")
    private String key;
    @Schema(description = Messages.FILTER_OPERATOR, type = "string", allowableValues = {"EQUAL","NOT_EQUAL","LIKE","IN","BETWEEN"})
    private Operator operator;
    @Schema(description = Messages.FILTER_FIELD_TYPE, type = "string", allowableValues = {"BOOLEAN","CHAR","LIKE","IN","BETWEEN"})
    private FieldType fieldType;
    @Schema(description = Messages.FILTER_VALUE, type = "string", example = "Programming")
    private transient Object value;
    @Schema(description = Messages.FILTER_VALUE_TO, type = "string", example = "11-03-2022 23:59:59")
    private transient Object valueTo;
    @Schema(description = Messages.FILTER_VALUES, type = "string", example = "[\"5.13\", \"5.8\"]")
    private transient List<Object> values;

}
