package com.example.app.service

import com.example.app.BaseTests
import com.example.app.domain.Course
import com.example.app.domain.Student
import com.example.app.exception.NotFoundException
import com.example.app.mock.entity.CourseMock
import com.example.app.mock.entity.StudentMock
import com.example.app.repository.CourseRepository
import com.example.app.repository.StudentRepository
import com.example.app.service.dto.CourseDTO
import com.example.app.service.dto.FullStudentDTO
import com.example.app.service.dto.StudentDTO
import com.example.app.service.mapper.*
import org.spockframework.util.Assert
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.context.annotation.Bean
import org.springframework.data.domain.Page
import org.springframework.data.domain.PageImpl
import org.springframework.data.domain.PageRequest
import org.springframework.data.domain.Pageable
import org.springframework.test.context.ContextConfiguration
import spock.lang.Unroll
import spock.mock.DetachedMockFactory

@ContextConfiguration(classes = [CourseServiceTest.Configuracion])
class CourseServiceTest extends BaseTests {

    @Autowired
    private CourseService courseService
    @Autowired
    private CourseRepository courseRepository
    @Autowired
    private StudentRepository studentRepository
    @Autowired
    private FullStudentMapper fullStudentMapper
    @Autowired
    private CourseMapper courseMapper
    @Autowired
    private StudentMapper studentMapper
    @Autowired
    private FullCourseMapper fullCourseMapper

    def setup() {
    }

    def "When we call create a course method we receive a created course"() {
        given: "Given a Course"
        CourseDTO courseDTO = courseMapper.toDto(new CourseMock().build())

        when: "Call the create method"
        def respuesta = courseService.save(courseDTO)

        then: "We receive an course with new id"
        1 * courseRepository.save(_) >> {
            arguments ->
                Course courseParam = arguments[0]
                assert courseParam.id == null
                courseParam.setId(1L)
                return courseParam
        }

        Assert.notNull(respuesta)
        respuesta.id == 1L
    }

    def "When we call findOne course method with an id and we don't found then we receive NotFoundException"() {
        given: "Given an inexistant id"
        def id = 99l

        when: "Call the findOne method"
        def respuesta = courseService.findOne(id)

        then: "We return an Option.empty"
        courseRepository.findOne(_) >> {
            return Optional.empty()
        }

        NotFoundException exception = thrown()
    }


    def "When we call delete course method with an id and we don't found then we receive NotFoundException"() {
        given: "Given an inexistant id"
        def id = 99L

        when: "Call the partial update method"
        def respuesta = courseService.delete(id)

        then: "We return an Option.empty"
        courseRepository.existsById(_) >> {
            return Optional.empty()
        }

        NotFoundException exception = thrown()
    }

    @Unroll
    def "When we call findAll method with filters (page: #page, size: #size, total: #total) we get the page of courses with first id #idFirstElement"() {
        given: "Given a page request"
        def Pageable pageable = PageRequest.of(page, size);

        // building mocking pages
        def courseList = new ArrayList()
        for (id in size * page..total) {
            courseList.add(createCourse(id + 1))
        }
        def pageFilter = new PageImpl<Student>(courseList);

        when: "Call the findAll method"
        Page<FullStudentDTO> filterPage = courseService.findAll(pageable)
        def filters = filterPage.getContent();

        then: "We get the pages"
        courseRepository.findAll(_) >> pageFilter

        filters.first().id == idFirstElement

        where:
        page | size | total | idFirstElement
        0    | 5    | 7     | 1
        1    | 6    | 10    | 7
        2    | 5    | 20    | 11

    }

    def "When we call enroll student to course method we receive the course updated with the enrolled student"() {
        given: "Given a id of course and list of student"
        def studentDTOs = new HashSet<StudentDTO>()
        studentDTOs.add(studentMapper.toDto(new StudentMock().setId(1L).build()))
        def id = 1L

        when: "Call the create method"
        def respuesta = courseService.registerStudentToCourse(id, studentDTOs)

        then: "We receive an course with new id"
        1 * courseRepository.findById(_) >> Optional.of(new CourseMock().build())
        1 * studentRepository.findById(_) >> Optional.of(new StudentMock().setId(1L).build())
        1 * courseRepository.save(_) >> new CourseMock().setId(1L)
                .addStudent(new StudentMock().setId(1L).build())
                .build()

        Assert.notNull(respuesta)
        respuesta.id == 1L
        respuesta.students[0].id == 1L
        respuesta.students[0].firstName == "Emilio"
        respuesta.students[0].lastName == "Dummy"
    }

    private Course createCourse(Long id) {
        def course = new CourseMock().build()
        course.setId(id)
        return course
    }

    static class Configuracion {

        def mockFactory = new DetachedMockFactory()

        @Bean
        CourseService courseService() {
            return new CourseService()
        }

        @Bean
        FullCourseMapper fullCourseMapper() {
            return new FullCourseMapperImpl()
        }

        @Bean
        FullStudentMapper fullStudentMapper() {
            return new FullStudentMapperImpl()
        }

        @Bean
        CourseMapper courseMapper() {
            return new CourseMapperImpl()
        }

        @Bean
        StudentMapper studentMapper() {
            return new StudentMapperImpl()
        }

        @Bean
        StudentRepository studentRepository() {
            return mockFactory.Mock(StudentRepository)
        }

        @Bean
        CourseRepository courseRepository() {
            return mockFactory.Mock(CourseRepository)
        }
    }


}
