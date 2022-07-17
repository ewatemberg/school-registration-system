package com.example.app.mock.entity

import com.example.app.domain.Student

class StudentMock {

    private Long id = null
    private String firstName = "Emilio"
    private String lastName = "Dummy"

    Student build() {
        Student student = new Student()
        student.setId(id)
        student.setFirstName(firstName)
        student.setLastName(lastName)
        return student
    }

    StudentMock setId(Long id) {
        this.id = id
        return this
    }

    StudentMock setFistName(String fistName) {
        this.fistName = fistName
        return this
    }

    StudentMock setLastName(String lastName) {
        this.lastName = lastName
        return this
    }

}
