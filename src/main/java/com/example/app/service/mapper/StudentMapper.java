package com.example.app.service.mapper;

import com.example.app.domain.Student;
import com.example.app.service.dto.StudentDTO;
import com.example.app.service.dto.StudentPatchDTO;
import org.mapstruct.BeanMapping;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.mapstruct.Named;
import org.mapstruct.NullValuePropertyMappingStrategy;
import org.mapstruct.ReportingPolicy;

/**
 * Mapper for the entity Student and its DTO StudentDTO.
 */
@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE, uses = {CourseMapper.class})
public interface StudentMapper extends EntityMapper<StudentDTO, Student> {

    StudentDTO toDto(Student student);

    Student toEntity(StudentDTO studentDTO);

    @Named("partialUpdate")
    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    void partialUpdate(@MappingTarget Student student, StudentPatchDTO dto);

    default Student fromId(Long id) {
        if (id == null) {
            return null;
        }
        Student student = new Student();
        student.setId(id);
        return student;
    }
}
