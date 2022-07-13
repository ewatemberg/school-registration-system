package com.example.app.resource.atdd

import com.example.app.domain.Student
import com.example.app.enums.ErrorType
import com.example.app.mock.StudentCRUDMock
import com.example.app.repository.StudentRepository
import com.example.app.resource.StudentResource
import com.example.app.service.StudentService
import com.example.app.utils.Messages
import org.springframework.beans.factory.BeanFactory
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.TestConfiguration
import org.springframework.test.context.ContextConfiguration
import org.springframework.test.web.servlet.MockMvc
import spock.mock.DetachedMockFactory

@ContextConfiguration
class GetStudentResourceTest extends com.example.app.config.AcceptanceSpecification {

    @Autowired
    MockMvc mockMvc
    @Autowired
    StudentRepository studentRepository;
    @Autowired
    StudentService studentService
    @Autowired
    private StudentResource resource

    private BeanFactory beanFactory = Mock(BeanFactory)
    private StudentCRUDMock mocks = new StudentCRUDMock()

    def student

    def setup() {
        student = studentRepository.save(Student.builder()
                .firstName("John")
                .lastName("Wick")
                .email("johnwick@localhost.com").build())
    }

    def "GET student with valid id"() {
        given: "Given an id of student"
        def id = student.getId()

        when: "We call get an student by id"
        def response = doGetStudentWith(id)

        then: "Obtain the student information"
        response.status == 200
        def responseJson = getJson(response.raw)
        responseJson.first_name == "John"
        responseJson.last_name == "Wick"
    }

    def "GET student with inexistent id"() {
        given: "Given an id inexistent"
        def id = 99L

        when: "We call get an student by id"
        def response = doGetStudentWith(id)

        then: "We get an error 400 with detail of problem"
        response.status == 404
        def responseJson = getJson(response.raw)
        responseJson.type == ErrorType.BUSINESS.toString()
        responseJson.detail == Messages.RESOURCES_NOT_FOUND
    }


    @TestConfiguration
    static class Configuracion {
        def mockFactory = new DetachedMockFactory()
    }

}
