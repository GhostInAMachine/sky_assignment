create sequence project_seq start with 1 increment by 50;
create sequence user_account_seq start with 1 increment by 50;
create table admin_account (password varchar(255), username varchar(255) not null, primary key (username));
create table project (id integer not null, user_id integer not null, name varchar(255) not null, primary key (id));
create table user_account (id integer not null, email varchar(255) not null, name varchar(255), password varchar(255) not null, primary key (id));
alter table if exists project add constraint pu foreign key (user_id) references user_account;