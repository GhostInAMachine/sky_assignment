create sequence project_seq start with 1 increment by 50;
create sequence user_account_seq start with 1 increment by 50;
create table admin_account (password varchar(255), username varchar(255) not null, primary key (username));
create table project (id integer not null, user_id integer, name varchar(255), primary key (id));
create table user_account (id integer not null, email varchar(255), name varchar(255), password varchar(255), primary key (id));
alter table if exists project add constraint FKbidpv6vwf9559nbfojuod6i63 foreign key (user_id) references user_account;