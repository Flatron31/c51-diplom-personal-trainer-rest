/*INSERT INTO users  (username, password, first_name, last_name, email, age, weight, growth, mission, status)
VALUES ('Arnold', 'test1', 'Arnold', 'Schwarzenegger', 'Schwarzenegger@mail.ru', 60, 92, 189, 'RELIEF', 'ACTIVE');

INSERT INTO roles (name, user_id) VALUES ('USER', 1);*/

--     create table users (
--         id bigint generated by default as identity,
--         username varchar(50),
--         password varchar(255),
--         first_name varchar(50),
--         last_name varchar(50),
--         email varchar(255),
--         age bigint not null check (age>=1),
--         weight bigint not null check (weight>=1), primary key (id),
--         growth bigint not null check (growth>=1),
--         mission varchar(255),
--         status varchar(255),
--     );
--
--     create table roles (
--                        id bigint generated by default as identity,
--                        name varchar(50),
--                        user_id bigint, primary key (id)
--     );