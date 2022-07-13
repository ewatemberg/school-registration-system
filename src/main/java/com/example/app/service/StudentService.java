package com.example.app.service;

import com.example.app.criteria.SearchRequest;
import com.example.app.criteria.SearchSpecification;
import com.example.app.domain.Course;
import com.example.app.domain.Student;
import com.example.app.enums.ErrorType;
import com.example.app.exception.NotFoundException;
import com.example.app.repository.StudentRepository;
import com.example.app.service.dto.StudentDTO;
import com.example.app.service.dto.StudentPatchDTO;
import com.example.app.service.mapper.StudentMapper;
import com.example.app.utils.Messages;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@Service
@Transactional
public class StudentService {

    @Autowired
    private StudentRepository studentRepository;
    @Autowired
    private StudentMapper studentMapper;

    public StudentDTO save(StudentDTO newStudentDTO) {
        log.debug("Request to save the student : {}", newStudentDTO);
        Student savedStudent = studentRepository.save(studentMapper.toEntity(newStudentDTO));
        return studentMapper.toDto(savedStudent);
    }

    @Transactional(readOnly = true)
    public Page<StudentDTO> findAll(Pageable pageable) {
        log.debug("Request to get all students");
        Page<Student> students = studentRepository.findAll(pageable);
        return students.map(studentMapper::toDto);
    }

    @Transactional(readOnly = true)
    public Page<StudentDTO> search(SearchRequest searchRequest) {
        log.debug("Request to search Student : {}", searchRequest);
        SearchSpecification<Student> specification = new SearchSpecification<>(searchRequest);
        Pageable pageable = SearchSpecification.getPageable(searchRequest.getPage(), searchRequest.getSize());
        return studentRepository.findAll(specification, pageable).map(studentMapper::toDto);
    }


    public StudentDTO partialUpdate(Long id, StudentPatchDTO studentDTO) {
        log.debug("Request to partially update Student : {}", studentDTO);
        if (!studentRepository.existsById(id))
            throw new NotFoundException(ErrorType.BUSINESS, Messages.RESOURCE_NOT_FOUND);
        Student student = studentRepository
                .findById(id)
                .map(existingMaintainer -> {
                    if (studentDTO.getFirstName() != null) {
                        existingMaintainer.setFirstName(studentDTO.getFirstName());
                    }
                    if (studentDTO.getLastName() != null) {
                        existingMaintainer.setLastName(studentDTO.getLastName());
                    }
                    if (studentDTO.getEmail() != null) {
                        existingMaintainer.setEmail(studentDTO.getEmail());
                    }
                    return existingMaintainer;
                })
                .map(studentRepository::save)
                .get();
        return studentMapper.toDto(student);
    }

    @Transactional(readOnly = true)
    public StudentDTO findOne(Long id) {
        log.debug("Request to get Student : {}", id);
        if (!studentRepository.existsById(id))
            throw new NotFoundException(ErrorType.BUSINESS, Messages.RESOURCE_NOT_FOUND);
        return studentMapper.toDto(studentRepository.findById(id).get());
    }

    public void delete(Long id) {
        log.debug("Request to delete Student : {}", id);
        if (!studentRepository.existsById(id))
            throw new NotFoundException(ErrorType.BUSINESS, Messages.RESOURCE_NOT_FOUND);
        studentRepository.deleteById(id);
    }

}
