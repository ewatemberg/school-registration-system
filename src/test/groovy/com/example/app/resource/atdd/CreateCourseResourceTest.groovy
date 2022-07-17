package com.example.app.resource.atdd

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
class CreateCourseResourceTest extends com.example.app.config.AcceptanceSpecification {

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

    def setup() {
    }

    def "POST course with valid data"() {
        given: "Given a request of course"
        def course = mocks.validCourse()

        when: "We post to create a course"
        def response = doPostCourseWith(course.json)

        then: "We get all data of course"
        response.status == 201
        def responseJson = getJson(response.raw)
        responseJson.name == "Language"
        responseJson.maximum_capacity == 50
        responseJson.registration_available == 50
    }

    def "POST course with invalid data name"() {
        given: "Given a request of student"
        def course = mocks.invalidCourseName()

        when: "We post to create an student"
        def response = doPostCourseWith(course.json)

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
