package com.example.app.resource.atdd

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
class CreateStudentResourceTest extends com.example.app.config.AcceptanceSpecification {

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

    def setup() {
    }

    def "POST student with valid data"() {
        given: "Given a request of student"
        def student = mocks.validStudent()

        when: "We post to create an student"
        def response = doPostStudentWith(student.json)

        then: "We get all data of student"
        response.status == 201
        def responseJson = getJson(response.raw)
        responseJson.first_name == "John"
        responseJson.last_name == "Wick"
    }

    def "POST students with invalid data name"() {
        given: "Given a request of student"
        def student = mocks.invalidStudentName()

        when: "We post to create an student"
        def response = doPostStudentWith(student.json)

        then: "We get an error 400 with detail of problem"
        response.status == 400
        def responseJson = getJson(response.raw)
        responseJson.type == ErrorType.BUSINESS.toString()
        responseJson.detail == Messages.BAD_REQUEST_INVALID_DATA
    }


    @TestConfiguration
    static class Configuracion {
        def mockFactory = new DetachedMockFactory()
    }

}
