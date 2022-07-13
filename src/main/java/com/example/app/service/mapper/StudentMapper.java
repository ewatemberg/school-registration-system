package com.example.app.service.mapper;

import com.example.app.domain.Student;
import com.example.app.service.dto.StudentDTO;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;

/**
 * Mapper for the entity Student and its DTO StudentDTO.
 */
@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE, uses = {})
public interface StudentMapper extends EntityMapper<StudentDTO, Student> {

    StudentDTO toDto(Student student);

    Student toEntity(StudentDTO studentDTO);

    default Student fromId(Long id) {
        if (id == null) {
            return null;
        }
        Student student = new Student();
        student.setId(id);
        return student;
    }
}
