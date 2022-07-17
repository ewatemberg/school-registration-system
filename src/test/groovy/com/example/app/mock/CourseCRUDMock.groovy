package com.example.app.mock

import com.example.app.service.dto.CourseDTO
import com.example.app.service.dto.StudentDTO

class CourseCRUDMock extends Mock{

  private static String BASE_PATH = "/json/course/"

  private static String PATH_VALID_COURSE = "${BASE_PATH}VALID_COURSE.json"
  private static String PATH_INVALID_COURES_NAME_NULL = "${BASE_PATH}INVALID_COURSE_INVALID_DATA.json"
  private static String PATH_UPDATE_PUT_COURSE = "${BASE_PATH}UPDATE_PUT_COURSE.json"
  private static String PATH_VALID_ENROLL_STUDENT_TO_COURSE = "${BASE_PATH}VALID_ENROLL_STUDENT_COURSE.json"

  Map validCourse() {
    buildMapResponse(PATH_VALID_COURSE, CourseDTO)
  }

  Map invalidCourseName() {
    buildMapResponse(PATH_INVALID_COURES_NAME_NULL, CourseDTO)
  }

  Map updatePutCourse() {
    buildMapResponse(PATH_UPDATE_PUT_COURSE, CourseDTO)
  }

  Map validEnrollStudentToCourse() {
    buildMapResponse(PATH_VALID_ENROLL_STUDENT_TO_COURSE, Set<StudentDTO>)
  }

}
