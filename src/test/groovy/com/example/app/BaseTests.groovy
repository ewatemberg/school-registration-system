package com.example.app

import com.fasterxml.jackson.databind.ObjectMapper
import spock.lang.Specification

class BaseTests extends Specification {

    private ObjectMapper objectMapper

    def setup() {
        objectMapper = new ObjectMapper()
    }

    def createObjetFromJson(String jsonPath, Class<?> clazzObject) {
      objectMapper.readValue(readJson(jsonPath), clazzObject)
    }

    def readJson(String jsonPath) {
      new File(getClass().getResource(jsonPath).toURI()).text
    }

}
