package com.example.app.resource.atdd

import com.example.app.domain.Course
import com.example.app.domain.Student
import com.example.app.enums.ErrorCode
import com.example.app.enums.ErrorType
import com.example.app.mock.CourseCRUDMock
import com.example.app.repository.CourseRepository
import com.example.app.repository.StudentRepository
import com.example.app.resource.CourseResource
import com.example.app.service.CourseService
import com.example.app.utils.Messages
import org.springframework.beans.factory.BeanFactory
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.TestConfiguration
import org.springframework.test.context.ContextConfiguration
import org.springframework.test.web.servlet.MockMvc
import spock.mock.DetachedMockFactory

@ContextConfiguration
class EnrollStudentToCourseResourceTest extends com.example.app.config.AcceptanceSpecification {

    @Autowired
    MockMvc mockMvc
    @Autowired
    StudentRepository studentRepository;
    @Autowired
    CourseRepository courseRepository;
    @Autowired
    CourseService courseService
    @Autowired
    private CourseResource resource

    private BeanFactory beanFactory = Mock(BeanFactory)
    private CourseCRUDMock mocks = new CourseCRUDMock()

    def student
    def course

    def setup() {
        student = studentRepository.save(Student.builder()
                .firstName("John")
                .lastName("Wick")
                .email("johnwick@localhost.com").build())
        course = courseRepository.save(Course.builder()
                .name("Language")
                .maximumCapacity(50)
                .registrationAvailable(50).build())
    }

    def "Add existing and inexisting students to course"() {
        given: "Given a request and existing course"
        def studentList = mocks.validEnrollStudentToCourse()
        def id = course.getId()

        when: "We post to create a course"
        def response = doPutEnrollStudentToCourseWith(id, studentList.json)

        then: "We get all data of course"
        response.status == 200
        def responseJson = getJson(response.raw)
        responseJson.name == "Language"
        responseJson.maximum_capacity == 50
        responseJson.registration_available == 48
    }

    def "Add existing student to existing course inexisting"() {
        given: "Given a request and existing course"
        def studentList = mocks.validEnrollStudentToCourse()
        def id = 99999L

        when: "We post to create a course"
        def response = doPutEnrollStudentToCourseWith(id, studentList.json)

        then: "Get error 404"
        response.status == 404
        def responseJson = getJson(response.raw)
        responseJson.type == ErrorType.BUSINESS.toString()
        responseJson.code == ErrorCode.NO_CODE.toString()
        responseJson.detail == Messages.RESOURCE_NOT_FOUND
    }


    @TestConfiguration
    static class Configuracion {
        def mockFactory = new DetachedMockFactory()
    }

}
