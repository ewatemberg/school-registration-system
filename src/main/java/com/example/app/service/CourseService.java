package com.example.app.service;

import com.example.app.criteria.SearchRequest;
import com.example.app.criteria.SearchSpecification;
import com.example.app.domain.Course;
import com.example.app.enums.ErrorType;
import com.example.app.exception.NotFoundException;
import com.example.app.repository.CourseRepository;
import com.example.app.service.dto.CourseDTO;
import com.example.app.service.mapper.CourseMapper;
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
public class CourseService {

    @Autowired
    private CourseRepository courseRepository;
    @Autowired
    private CourseMapper courseMapper;

    public CourseDTO save(CourseDTO newCourseDTO) {
        log.debug("Request to save the course : {}", newCourseDTO);
        Course savedCourse = courseRepository.save(courseMapper.toEntity(newCourseDTO));
        return courseMapper.toDto(savedCourse);
    }

    @Transactional(readOnly = true)
    public Page<CourseDTO> findAll(Pageable pageable) {
        log.debug("Request to get all courses");
        Page<Course> courses = courseRepository.findAll(pageable);
        return courses.map(courseMapper::toDto);
    }

    @Transactional(readOnly = true)
    public CourseDTO findOne(Long id) {
        log.debug("Request to get Course : {}", id);
        if (!courseRepository.existsById(id))
            throw new NotFoundException(ErrorType.BUSINESS, Messages.RESOURCE_NOT_FOUND);
        return courseMapper.toDto(courseRepository.findById(id).get());
    }

    @Transactional(readOnly = true)
    public Page<CourseDTO> search(SearchRequest searchRequest) {
        log.debug("Request to search Course : {}", searchRequest);
        SearchSpecification<Course> specification = new SearchSpecification<>(searchRequest);
        Pageable pageable = SearchSpecification.getPageable(searchRequest.getPage(), searchRequest.getSize());
        return courseRepository.findAll(specification, pageable).map(courseMapper::toDto);
    }

    public void delete(Long id) {
        log.debug("Request to delete Course : {}", id);
        if (!courseRepository.existsById(id))
            throw new NotFoundException(ErrorType.BUSINESS, Messages.RESOURCE_NOT_FOUND);
        courseRepository.deleteById(id);
    }

}
