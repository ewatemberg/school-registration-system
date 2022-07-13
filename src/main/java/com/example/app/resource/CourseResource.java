package com.example.app.resource;

import com.example.app.config.ApiVersion;
import com.example.app.criteria.SearchRequest;
import com.example.app.enums.ErrorType;
import com.example.app.exception.ApiError;
import com.example.app.exception.BadRequestException;
import com.example.app.service.CourseService;
import com.example.app.service.dto.CourseDTO;
import com.example.app.service.dto.StudentDTO;
import com.example.app.service.dto.StudentPatchDTO;
import com.example.app.utils.Messages;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@Tag(name = "Courses", description = "operations associated to courses")
@Slf4j
@RestController
@RequestMapping("/api")
public class CourseResource {

    public static final String PATH = "/courses";
    public static final String PATH_ID = PATH + "/{id}";
    public static final String PATH_SEARCH = PATH + "/search";

    @Autowired
    private CourseService courseService;

    @Operation(summary = "Create a new Course")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = Messages.RESOURCE_CREATED,
                    content = {@Content(mediaType = "application/json", schema = @Schema(implementation = CourseDTO.class))}),
            @ApiResponse(responseCode = "500", description = Messages.INTERNAL_ERROR,
                    content = {@Content(mediaType = "application/json", schema = @Schema(implementation = ApiError.class))})
    })
    @PostMapping(value = ApiVersion.V1 + PATH)
    public ResponseEntity<CourseDTO> create(@Valid @RequestBody CourseDTO courseDTO) {
        log.debug("REST request to save Course : {}", courseDTO);
        if (courseDTO.getId() != null) {
            throw new BadRequestException(ErrorType.BUSINESS, Messages.BAD_REQUEST_ID);
        }
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(courseService.save(courseDTO));
    }

    @Operation(summary = "Update a course completely and if it doesn't exist it creates it")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = Messages.RESOURCE_UPDATED,
                    content = {@Content(mediaType = "application/json", schema = @Schema(implementation = CourseDTO.class))}),
            @ApiResponse(responseCode = "500", description = Messages.INTERNAL_ERROR,
                    content = {@Content(mediaType = "application/json", schema = @Schema(implementation = ApiError.class))})
    })
    @PutMapping(ApiVersion.V1 + PATH_ID)
    public ResponseEntity<CourseDTO> update(
            @PathVariable(value = "id") final Long id,
            @RequestBody CourseDTO courseDTO) {
        log.debug("REST request to update Course : {}", id);
        return ResponseEntity
                .ok()
                .body(courseService.save(courseDTO));
    }

    @Operation(summary = "Get all Courses with pagination and filters")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = Messages.OPERATION_SUCCESSFUL,
                    content = {@Content(mediaType = "application/json", schema = @Schema(implementation = CourseDTO.class))}),
            @ApiResponse(responseCode = "500", description = Messages.INTERNAL_ERROR,
                    content = {@Content(mediaType = "application/json", schema = @Schema(implementation = ApiError.class))})
    })
    @GetMapping(ApiVersion.V1 + PATH)
    public ResponseEntity<Page<CourseDTO>> getAll(Pageable pageable) {
        log.debug("REST request to get a page of Courses");
        return ResponseEntity.ok().body(courseService.findAll(pageable));
    }

    @Operation(summary = "Search Courses by criteria")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = Messages.OPERATION_SUCCESSFUL,
                    content = {@Content(mediaType = "application/json", schema = @Schema(implementation = CourseDTO.class))}),
            @ApiResponse(responseCode = "500", description = Messages.INTERNAL_ERROR,
                    content = {@Content(mediaType = "application/json", schema = @Schema(implementation = ApiError.class))})
    })
    @PostMapping(ApiVersion.V1 + PATH_SEARCH)
    public ResponseEntity<Page<CourseDTO>> search(@RequestBody SearchRequest searchRequest) {
        log.debug("REST request to search Courses by criteria");
        return ResponseEntity.ok().body(courseService.search(searchRequest));
    }

    @Operation(summary = "Get a Course by id")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = Messages.OPERATION_SUCCESSFUL,
                    content = {@Content(mediaType = "application/json", schema = @Schema(implementation = CourseDTO.class))}),
            @ApiResponse(responseCode = "500", description = Messages.INTERNAL_ERROR,
                    content = {@Content(mediaType = "application/json", schema = @Schema(implementation = ApiError.class))}),
            @ApiResponse(responseCode = "404", description = Messages.RESOURCE_NOT_FOUND,
                    content = {@Content(mediaType = "application/json", schema = @Schema(implementation = ApiError.class))}),
    })
    @GetMapping(ApiVersion.V1 + PATH_ID)
    public ResponseEntity<CourseDTO> get(@PathVariable Long id) {
        log.debug("REST request to get Course : {}", id);
        return ResponseEntity
                .ok()
                .body(courseService.findOne(id));
    }

    @Operation(summary = "Delete a Course by id")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = Messages.RESOURCE_DELETED,
                    content = {@Content(mediaType = "application/json", schema = @Schema(implementation = CourseDTO.class))}),
            @ApiResponse(responseCode = "500", description = Messages.INTERNAL_ERROR,
                    content = {@Content(mediaType = "application/json", schema = @Schema(implementation = ApiError.class))}),
            @ApiResponse(responseCode = "404", description = Messages.RESOURCE_NOT_FOUND,
                    content = {@Content(mediaType = "application/json", schema = @Schema(implementation = ApiError.class))}),
    })
    @DeleteMapping(ApiVersion.V1 + PATH_ID)
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        log.debug("REST request to delete Course : {}", id);
        courseService.delete(id);
        return ResponseEntity.noContent().build();
    }


}
