package com.example.app.mock.entity

import com.example.app.domain.Course
import com.example.app.domain.Student

class CourseMock {

    private Long id = null
    private String name = "Language"
    private int maximumCapacity = 5
    private int registrationAvailable = 0
    private Set<Student> studentList = new HashSet<>()

    Course build() {
        Course course = new Course()
        course.setId(id)
        course.setName(name)
        course.setMaximumCapacity(maximumCapacity)
        course.setRegistrationAvailable(registrationAvailable)
        course.setStudents(studentList)
        return course
    }

    CourseMock setId(Long id) {
        this.id = id
        return this
    }

    CourseMock setName(String name) {
        this.name = name
        return this
    }

    CourseMock setMaximumCapacity(int maximumCapacity) {
        this.maximumCapacity = maximumCapacity
        return this
    }

    CourseMock setRegistrationAvailable(int registrationAvailable) {
        this.registrationAvailable = registrationAvailable
        return this
    }

    CourseMock addStudent(Student student) {
        this.studentList.add(student)
        return this
    }

}
