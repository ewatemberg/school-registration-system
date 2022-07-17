package com.example.app.service;

import com.example.app.criteria.SearchRequest;
import com.example.app.criteria.SearchSpecification;
import com.example.app.domain.Course;
import com.example.app.domain.Student;
import com.example.app.enums.ErrorType;
import com.example.app.exception.NotFoundException;
import com.example.app.repository.CourseRepository;
import com.example.app.repository.StudentRepository;
import com.example.app.service.dto.CourseDTO;
import com.example.app.service.dto.FullCourseDTO;
import com.example.app.service.dto.StudentDTO;
import com.example.app.service.mapper.CourseMapper;
import com.example.app.service.mapper.FullCourseMapper;
import com.example.app.utils.Messages;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashSet;
import java.util.Set;

@Slf4j
@Service
@Transactional
public class CourseService {

    @Autowired
    private CourseRepository courseRepository;
    @Autowired
    private StudentRepository studentRepository;
    @Autowired
    private CourseMapper courseMapper;
    @Autowired
    private FullCourseMapper fullCourseMapper;

    public CourseDTO save(CourseDTO newCourseDTO) {
        log.debug("Request to save the course : {}", newCourseDTO);
        Course savedCourse = courseRepository.save(courseMapper.toEntity(newCourseDTO));
        return courseMapper.toDto(savedCourse);
    }

    @Transactional(readOnly = true)
    public Page<FullCourseDTO> findAll(Pageable pageable) {
        log.debug("Request to get all courses");
        Page<Course> courses = courseRepository.findAll(pageable);
        return courses.map(fullCourseMapper::toDto);
    }

    @Transactional(readOnly = true)
    public CourseDTO findOne(Long id) {
        log.debug("Request to get Course : {}", id);
        if (!courseRepository.existsById(id))
            throw new NotFoundException(ErrorType.BUSINESS, Messages.RESOURCE_NOT_FOUND);
        return courseMapper.toDto(courseRepository.findById(id).get());
    }

    @Transactional(readOnly = true)
    public Page<FullCourseDTO> search(SearchRequest searchRequest) {
        log.debug("Request to search Course : {}", searchRequest);
        SearchSpecification<Course> specification = new SearchSpecification<>(searchRequest);
        Pageable pageable = SearchSpecification.getPageable(searchRequest.getPage(), searchRequest.getSize());
        return courseRepository.findAll(specification, pageable).map(fullCourseMapper::toDto);
    }

    public void delete(Long id) {
        log.debug("Request to delete Course : {}", id);
        if (!courseRepository.existsById(id))
            throw new NotFoundException(ErrorType.BUSINESS, Messages.RESOURCE_NOT_FOUND);
        courseRepository.deleteById(id);
    }

    public FullCourseDTO registerStudentToCourse(Long courseId, Set<StudentDTO> studentList) {
        log.debug("Course id : {} , Student:{}", courseId, studentList);
        Course course = courseRepository.findById(courseId).orElseThrow(new NotFoundException(ErrorType.BUSINESS, Messages.RESOURCE_NOT_FOUND));
        studentList.forEach(studentDTO -> {
                    Student student = getOrCreate(studentDTO);
                    student.incrementEnrollment();
                    course.addStudent(student);
                    course.decrementRegistrationAvailable();
                }
        );
        if (course.getStudents().size() > course.getMaximumCapacity())
            throw new NotFoundException(ErrorType.BUSINESS, String.format(Messages.BAD_REQUEST_MAX_CAPACITY, course.getRegistrationAvailable()));
        return fullCourseMapper.toDto(courseRepository.save(course));
    }

    private Student getOrCreate(StudentDTO studentDTO) {
        if (studentDTO.getId() != null) {
            return studentRepository.findById(studentDTO.getId()).orElseThrow(new NotFoundException(ErrorType.BUSINESS, Messages.RESOURCE_NOT_FOUND));
        } else {
            log.info("The student with lastname: {} not exist into BD. Adding...", studentDTO.getLastName());
            return new Student().builder()
                    .firstName(studentDTO.getFirstName())
                    .lastName(studentDTO.getLastName())
                    .email(studentDTO.getEmail())
                    .courses(new HashSet<>())
                    .build();
        }
    }
}
