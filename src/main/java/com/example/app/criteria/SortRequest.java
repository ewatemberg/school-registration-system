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

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
@Schema
public class SortRequest implements Serializable {

    private static final long serialVersionUID = 3194362295851723069L;
    @Schema(description = Messages.SORT_KEY, type = "string", example = "name")
    private String key;
    @Schema(description = Messages.SORT_DIRECTION, type = "string", allowableValues = {"ASC","DESC"})
    private SortDirection direction;

}
