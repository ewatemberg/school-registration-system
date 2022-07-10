CREATE TABLE course_student
(
    course_id  bigint not null,
    student_id bigint not null,
    primary key (`course_id`, `student_id`),
    constraint fk_course_id foreign key (course_id) references course (id),
    constraint fk_student_id foreign key (student_id) references student (id)
);