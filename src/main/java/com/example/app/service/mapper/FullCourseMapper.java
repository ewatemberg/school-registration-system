package com.example.app.service.mapper;

import com.example.app.domain.Course;
import com.example.app.service.dto.FullCourseDTO;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;

/**
 * Mapper for the entity Student and its DTO FullStudentDTO.
 */
@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE, uses = {StudentMapper.class})
public interface FullCourseMapper extends EntityMapper<FullCourseDTO, Course> {

    FullCourseDTO toDto(Course course);

    Course toEntity(FullCourseDTO fullCourseDTO);

}
