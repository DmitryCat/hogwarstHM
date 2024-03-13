create table human (
id serial primary key,
name character varying(50),
age integer,
has_license boolean,
car_id integer references car(id)
);

create table car (
id serial primary key,
brand character varying(50),
model character varying(50),
cost integer
);