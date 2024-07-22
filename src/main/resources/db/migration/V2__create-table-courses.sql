create table courses(
    id bigserial not null,
    course_name varchar(100) not null unique,
    category varchar(50) not null,
    primary key(id),
    constraint chk_course_name_not_empty check (course_name <> '')
)