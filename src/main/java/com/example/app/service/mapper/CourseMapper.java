package com.example.app.service.mapper;

import com.example.app.domain.Course;
import com.example.app.domain.Student;
import com.example.app.service.dto.CourseDTO;
import com.example.app.service.dto.StudentDTO;
import com.example.app.service.dto.StudentPatchDTO;
import org.mapstruct.BeanMapping;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;
import org.mapstruct.Named;
import org.mapstruct.NullValuePropertyMappingStrategy;
import org.mapstruct.ReportingPolicy;

/**
 * Mapper for the entity Course and its DTO CourseDTO.
 */
@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE, uses = {StudentMapper.class})
public interface CourseMapper extends EntityMapper<CourseDTO, Course> {

    CourseDTO toDto(Course student);

    Course toEntity(CourseDTO studentDTO);

    @Named("partialUpdate")
    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    void partialUpdate(@MappingTarget Course student, CourseDTO dto);

    default Course fromId(Long id) {
        if (id == null) {
            return null;
        }
        Course course = new Course();
        course.setId(id);
        return course;
    }
}
