create table Usuarios(
id bigserial not null,
nombre varchar(100) not null,
email varchar(100) not null,
password varchar(255) not null
primary key(id)
)