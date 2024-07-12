create table courses (
    id bigserial not null,
    course_name varchar(100) not null,
    category varchar(50) not null,
    primary key(id)
)