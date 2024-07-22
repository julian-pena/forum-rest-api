create table topics (
    id bigserial not null,
    title varchar(255) not null unique,
    message varchar(2000) not null,
    creation_date timestamp not null,
    status varchar(50) not null,
    author_id bigint not null,
    course_id bigint not null,
    primary key (id),
    foreign key (author_id) references users(id),
    foreign key (course_id) references courses(id),
    constraint chk_title_not_empty check (title <> '')
);
