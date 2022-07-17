package com.example.app.mock

import com.fasterxml.jackson.annotation.JsonInclude.Include
import com.fasterxml.jackson.databind.ObjectMapper
import com.fasterxml.jackson.datatype.jdk8.Jdk8Module
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule

class Mock {

    private ObjectMapper objectMapper = new ObjectMapper()
    private ObjectMapper objectMapperIgnoringNulls = new ObjectMapper()

    public Mock() {
        objectMapper.registerModule(new Jdk8Module());
        objectMapperIgnoringNulls.setSerializationInclusion(Include.NON_NULL)
    }

    protected Map buildMapResponse(String path, Class clazz) {

        def object = getObjectFromJson(path, clazz)

        [json        : objectToString(object),
         objeto      : object,
         jsonFromPath: getJson(path)]
    }

    def getObjectFromJson(String path, Class clazz) {
        objectMapper.readValue(getClass().getResource(path), clazz)
    }

    def objectToString(Object object) {
        objectMapper.writeValueAsString(object)
    }

    def getJson(String path) {
        new File(getClass().getResource(path).toURI()).text
    }

}
