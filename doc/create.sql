-- Создаем базу данных
-- После создания базы данных можно выполнить остальные команды в КОНТЕКСТЕ (подключившись) БД sm
create database sm;


-- Создаем последовательность для автоматической нумерации поля id таблицы users
CREATE SEQUENCE if not exists users_seq;

-- Создаем последовательность для автоматической нумерации поля id таблицы messages
CREATE SEQUENCE if not exists msg_seq;

-- Создаем таблицу хранящую пользователей системы
CREATE TABLE if not exists users
(
	-- Идентификатор, автоматически нумеруется посредством обращения к последовательности users_seq
    id integer NOT NULL DEFAULT nextval('users_seq') PRIMARY KEY, 
    username character varying(255) NOT NULL, -- Имя пользователя (запрещаем пустое поле)
    login character varying(255)  NOT NULL, -- Логин (запрещаем пустое поле)
    password character varying(255) COLLATE  NOT NULL, -- Пароль (запрещаем пустое поле)
    seans_id uuid -- Идентификатор-токен для работы системы
);

CREATE TABLE if not exists messages
(
	-- Идентификатор, автоматически нумеруется посредством обращения к последовательности msg_seq
    id integer NOT NULL DEFAULT nextval('msg_seq') PRIMARY KEY,
    fromuser integer NOT NULL, -- Код пользователя-отправителя (запрещаем пустое поле)
    touser integer NOT NULL, -- Код пользователя-получателя (запрещаем пустое поле)
    content text COLLATE pg_catalog."default" NOT NULL DEFAULT ''::text, -- Сообщение (запрещаем отсутствие данных, по умолчанию пустая строка)
    datemsg timestamp without time zone NOT NULL DEFAULT now(), -- Время отправки сообщения (запрещаем пустое поле, по умолчанию текущее время)
    readed boolean NOT NULL DEFAULT false -- Индикатор прочитанного сообщения (запрещаем пустое поле)
);

-- создаем двух пользователей для тестирования
insert into users (username, login, password) values ('User1', 'user1', 'user1');
insert into users (username, login, password) values ('User2', 'user2', 'user2');