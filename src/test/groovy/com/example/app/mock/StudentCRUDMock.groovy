package com.example.app.mock

import com.example.app.service.dto.StudentDTO


class StudentCRUDMock extends Mock{

  private static String BASE_PATH = "/json/student/"

  private static String PATH_VALID_STUDENT = "${BASE_PATH}VALID_STUDENT.json"
  private static String PATH_INVALID_STUDENT_NAME_NULL = "${BASE_PATH}INVALID_STUDENT_INVALID_DATA.json"
  private static String PATH_UPDATE_PUT_STUDENT = "${BASE_PATH}UPDATE_PUT_STUDENT.json"
  private static String PATH_UPDATE_PATCH_STUDENT = "${BASE_PATH}UPDATE_PATCH_STUDENT.json"

  Map validStudent() {
    buildMapResponse(PATH_VALID_STUDENT, StudentDTO)
  }

  Map invalidStudentName() {
    buildMapResponse(PATH_INVALID_STUDENT_NAME_NULL, StudentDTO)
  }

  Map updatePutStudent() {
    buildMapResponse(PATH_UPDATE_PUT_STUDENT, StudentDTO)
  }

  Map updatePatchStudent() {
    buildMapResponse(PATH_UPDATE_PATCH_STUDENT, StudentDTO)
  }

}
