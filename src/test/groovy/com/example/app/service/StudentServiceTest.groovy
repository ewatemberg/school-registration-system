package com.example.app.service

import com.example.app.BaseTests
import com.example.app.domain.Student
import com.example.app.exception.NotFoundException
import com.example.app.mock.entity.StudentMock
import com.example.app.repository.StudentRepository
import com.example.app.service.dto.FullStudentDTO
import com.example.app.service.dto.StudentDTO
import com.example.app.service.dto.StudentPatchDTO
import com.example.app.service.mapper.CourseMapper
import com.example.app.service.mapper.CourseMapperImpl
import com.example.app.service.mapper.FullStudentMapper
import com.example.app.service.mapper.FullStudentMapperImpl
import com.example.app.service.mapper.StudentMapper
import com.example.app.service.mapper.StudentMapperImpl
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

@ContextConfiguration(classes = [StudentServiceTest.Configuracion])
class StudentServiceTest extends BaseTests {

    @Autowired
    private StudentService studentService
    @Autowired
    private StudentMapper studentMapper
    @Autowired
    private StudentRepository studentRepository
    @Autowired
    private FullStudentMapper fullStudentMapper
    @Autowired
    private CourseMapper courseMapper

    def setup() {
    }

    def "When we call create a student method we receive a created student"() {
        given: "Given a student"
        StudentDTO studentDTO = studentMapper.toDto(new StudentMock().build())

        when: "Call the create method"
        def respuesta = studentService.save(studentDTO)

        then: "We receive an student with new id"
        1 * studentRepository.save(_) >> {
            arguments ->
                Student studentParam = arguments[0]
                assert studentParam.id == null
                studentParam.setId(1L)
                return studentParam
        }

        Assert.notNull(respuesta)
        respuesta.id == 1L
    }

    def "When we call findOne student method with an id and we don't found then we receive NotFoundException"() {
        given: "Given an inexistant id"
        def id = 99l

        when: "Call the findOne method"
        def respuesta = studentService.findOne(id)

        then: "We return an Option.empty"
        studentRepository.findOne(_) >> {
            return Optional.empty()
        }

        NotFoundException exception = thrown()
    }

    def "When we call partial update student method with an id and we don't found then we receive NotFoundException"() {
        given: "Given an inexistant id"
        def id = 99L
        def studentDTO = StudentPatchDTO.builder().firstName("Peter").build()

        when: "Call the partial update method"
        def respuesta = studentService.partialUpdate(id, studentDTO)

        then: "We return an Option.empty"
        studentRepository.existsById(_) >> {
            return Optional.empty()
        }

        NotFoundException exception = thrown()
    }


    def "When we call delete student method with an id and we don't found then we receive NotFoundException"() {
        given: "Given an inexistant id"
        def id = 99L

        when: "Call the partial update method"
        def respuesta = studentService.delete(id)

        then: "We return an Option.empty"
        studentRepository.existsById(_) >> {
            return Optional.empty()
        }

        NotFoundException exception = thrown()
    }

    @Unroll
    def "When we call findAll method with filters (page: #page, size: #size, total: #total) we get the page of students with first id #idFirstElement"() {
        given: "Given a page request"
        def Pageable pageable = PageRequest.of(page, size);

        // building mocking pages
        def studentList = new ArrayList()
        for (id in size * page..total) {
            studentList.add(createStudent(id + 1))
        }
        def pageFilter = new PageImpl<Student>(studentList);

        when: "Call the findAll method"
        Page<FullStudentDTO> filterPage = studentService.findAll(pageable)
        def filters = filterPage.getContent();

        then: "We get the pages"
        studentRepository.findAll(_) >> pageFilter

        filters.first().id == idFirstElement

        where:
        page | size | total | idFirstElement
        0    | 5    | 7     | 1
        1    | 6    | 10    | 7
        2    | 5    | 20    | 11

    }

    private Student createStudent(Long id) {
        def student = new StudentMock().build()
        student.setId(id)
        return student
    }

    static class Configuracion {

        def mockFactory = new DetachedMockFactory()

        @Bean
        StudentService studentService() {
            return new StudentService()
        }

        @Bean
        StudentMapper studentMapper() {
            return new StudentMapperImpl()
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
        StudentRepository studentRepository() {
            return mockFactory.Mock(StudentRepository)
        }
    }


}
