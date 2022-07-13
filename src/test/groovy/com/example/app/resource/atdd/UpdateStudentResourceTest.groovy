package com.example.app.resource.atdd

import com.example.app.domain.Student
import com.example.app.mock.StudentCRUDMock
import com.example.app.repository.StudentRepository
import com.example.app.resource.StudentResource
import com.example.app.service.StudentService
import org.springframework.beans.factory.BeanFactory
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.TestConfiguration
import org.springframework.test.context.ContextConfiguration
import org.springframework.test.web.servlet.MockMvc
import spock.mock.DetachedMockFactory

@ContextConfiguration
class UpdateStudentResourceTest extends com.example.app.config.AcceptanceSpecification {

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

    def student;

    def setup() {
        student = studentRepository.save(Student.builder()
                .firstName("John")
                .lastName("Wick")
                .email("johnwick@localhost.com").build())
    }

    def "PUT student with valid student id"() {
        given: "Given an id of student"
        def id = student.getId()
        def studentToUpdate = mocks.updatePutStudent()

        when: "We get an student by id"
        def response = doPutStudentWith(id, studentToUpdate.json)

        then: "Obtain the student information"
        response.status == 200
        def responseJson = getJson(response.raw)
        responseJson.first_name == "James"
        responseJson.last_name == "Bond"
    }

    def "PATCH student with valid student id"() {
        given: "Given an id of student"
        def id = student.getId()
        def studentToUpdate = mocks.updatePatchStudent()

        when: "We get an student by id"
        def response = doPatchStudentWith(id, studentToUpdate.json)

        then: "Obtain the student information"
        response.status == 200
        def responseJson = getJson(response.raw)
        responseJson.first_name == "John"
        responseJson.last_name == "Maradona"
    }


    @TestConfiguration
    static class Configuracion {
        def mockFactory = new DetachedMockFactory()
    }

}
