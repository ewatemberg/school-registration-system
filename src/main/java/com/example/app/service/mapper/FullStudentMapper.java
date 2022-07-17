package com.example.app.service.mapper;

import com.example.app.domain.Student;
import com.example.app.service.dto.FullStudentDTO;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.ReportingPolicy;

/**
 * Mapper for the entity Student and its DTO FullStudentDTO.
 */
@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE, uses = {CourseMapper.class})
public interface FullStudentMapper extends EntityMapper<FullStudentDTO, Student> {

    @Mapping(source = "courses", target = "courseDTO")
    FullStudentDTO toDto(Student student);

    @Mapping(source = "courseDTO", target = "courses")
    Student toEntity(FullStudentDTO studentDTO);

}
