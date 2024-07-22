create table users(
    id bigserial not null,
    user_name varchar(100) not null,
    email varchar(100) not null unique,
    password varchar(255) not null,
    registration_date date not null,
    primary key(id),
    constraint chk_email_not_empty check (email <> '')
)