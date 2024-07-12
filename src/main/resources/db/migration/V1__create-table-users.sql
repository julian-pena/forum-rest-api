create table users(
    id bigserial not null,
    user_name varchar(100) not null,
    email varchar(100) not null,
    password varchar(255) not null,
    creation_date date not null,
    primary key(id)
)