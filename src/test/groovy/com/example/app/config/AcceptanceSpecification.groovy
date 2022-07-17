package com.example.app.config

import com.example.app.Application
import groovy.json.JsonSlurper
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.core.AutoConfigureCache
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.test.context.ActiveProfiles
import org.springframework.test.context.TestPropertySource
import org.springframework.test.context.web.WebAppConfiguration
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.setup.MockMvcBuilders
import org.springframework.web.context.WebApplicationContext
import spock.lang.Specification

@SpringBootTest(classes = Application.class)
@AutoConfigureMockMvc
@AutoConfigureCache
@TestPropertySource(
        locations = "classpath:application.properties",
        properties = [
                "spring.main.allow-bean-definition-overriding=true",
                "spring.http.encoding.force=true"
        ]
)
@WebAppConfiguration
@ActiveProfiles("local")
class AcceptanceSpecification extends Specification {

    JsonSlurper jsonSlurper = new JsonSlurper()

    @Autowired
    private WebApplicationContext context;
    @Autowired
    MockMvc mockMvc

    def setup() {
        mockMvc = MockMvcBuilders
                .webAppContextSetup(context)
                .build();
    }

    def cleanup() {
    }

    Map doPostStudentWith(String body) {
        buildMap(IntegrationTestRequestHelper.POST(mockMvc, IntegrationTestRequestHelper.PATH_STUDENT, body))
    }

    Map doGetStudentWith(Long id) {
        buildMap(IntegrationTestRequestHelper.GET(mockMvc, "${IntegrationTestRequestHelper.PATH_STUDENT}/${id}"))
    }

    Map doPutStudentWith(Long id, String body) {
        buildMap(IntegrationTestRequestHelper.PUT(mockMvc, "${IntegrationTestRequestHelper.PATH_STUDENT}/${id}", body))
    }

    Map doPatchStudentWith(Long id, String body) {
        buildMap(IntegrationTestRequestHelper.PATCH(mockMvc, "${IntegrationTestRequestHelper.PATH_STUDENT}/${id}", body))
    }

    Map doGetStudentWithSearch(String search) {
        buildMap(IntegrationTestRequestHelper.GET(mockMvc, "${IntegrationTestRequestHelper.PATH_STUDENT}?${search}"))
    }

    Map doDeleteStudentWith(Long id) {
        buildMap(IntegrationTestRequestHelper.DELETE(mockMvc, "${IntegrationTestRequestHelper.PATH_STUDENT}/${id}"))
    }

    Map doPostCourseWith(String body) {
        buildMap(IntegrationTestRequestHelper.POST(mockMvc, IntegrationTestRequestHelper.PATH_COURSE, body))
    }

    Map doPutEnrollStudentToCourseWith(Long id, String body) {
        buildMap(IntegrationTestRequestHelper.PUT(mockMvc, "${IntegrationTestRequestHelper.PATH_ENROLL_STUDENT_TO_COURSE}/${id}", body))
    }

    private Map buildMap(def response) {
        def responseParsed = (response.contentAsString as String).isBlank() ? "" : jsonSlurper.parseText(response.contentAsString)
        [body  : responseParsed,
         status: response.status,
         raw   : response.contentAsString]
    }

    protected def getJson(String jsonAsString) {
        jsonSlurper.parseText(jsonAsString)
    }

}
