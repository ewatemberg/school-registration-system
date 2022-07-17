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
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
@Schema
public class SearchRequest implements Serializable {
    private static final long serialVersionUID = 8514625832019794838L;

    private List<FilterRequest> filters;
    private List<SortRequest> sorts;
    @Schema(description = Messages.SEARCH_PAGE, type = "int", example = "0")
    private Integer page;
    @Schema(description = Messages.SEARCH_SIZE, type = "int", example = "20")
    private Integer size;

    public List<FilterRequest> getFilters() {
        if (Objects.isNull(this.filters)) return new ArrayList<>();
        return this.filters;
    }

    public List<SortRequest> getSorts() {
        if (Objects.isNull(this.sorts)) return new ArrayList<>();
        return this.sorts;
    }
}
