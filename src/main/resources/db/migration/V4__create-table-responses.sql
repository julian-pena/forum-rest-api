create table responses (
    id bigserial not null,
    message varchar(1000) not null,
    topic bigint not null,
    creation_date timestamp not null,
    responder bigint not null,
    solution boolean,
    primary key (id),
    foreign key (topic) references topics(id),
    foreign key (responder) references users(id),
    constraint chk_message_not_empty check (message <> '')
);
