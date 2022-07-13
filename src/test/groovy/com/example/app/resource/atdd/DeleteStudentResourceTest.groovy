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
class DeleteStudentResourceTest extends com.example.app.config.AcceptanceSpecification {

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

    def "DELETE student with valid id"() {
        given: "Given an id of student"
        def student = studentRepository.save(Student.builder()
                .firstName("John")
                .lastName("Wick")
                .email("johnwick@localhost.com").build())
        def id = student.getId()

        when: "We delete an student by id"
        def response = doDeleteStudentWith(id)

        then: "Obtain the student information"
        response.status == 204
    }


    @TestConfiguration
    static class Configuracion {
        def mockFactory = new DetachedMockFactory()
    }

}
