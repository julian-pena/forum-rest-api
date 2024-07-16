create table responses (
    id bigserial not null,
    message varchar(1000) not null,
    topic_id bigint not null,
    creation_date timestamp not null,
    responder_id bigint not null,
    solution boolean,
    primary key (id),
    foreign key (topic_id) references topics(id),
    foreign key (responder_id) references users(id),
    constraint chk_message_not_empty check (message <> '')
);
