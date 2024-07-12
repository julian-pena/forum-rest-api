create table topics (
    id bigserial not null,
    title varchar(255) not null,
    message varchar(2000) not null,
    creation_date timestamp not null,
    status varchar(50) not null,
    author bigint not null,
    course bigint not null,
    primary key (id),
    foreign key (author) references users(id),
    foreign key (course) references courses(id)
);
