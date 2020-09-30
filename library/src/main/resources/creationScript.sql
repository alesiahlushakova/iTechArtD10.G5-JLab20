create database library;
use library;
drop table if exists book_order;
drop table if exists book_author;
drop table if exists book_genre;
drop table if exists reader;
drop table if exists author;
drop table if exists genre;
drop table if exists book;
create table reader (
                        id int primary key auto_increment not null,
                        first_name varchar(255) not null,
                        last_name varchar(255) not null,
                        email varchar(255) not null,
                        gender bool not null,
                        phone varchar(12) not null,
                        date_of_registration timestamp default current_timestamp not null
);

create table author (
                        id int primary key auto_increment not null,
                        first_name varchar(255) not null,
                        last_name varchar(255) not null
);

create table genre (
                       id int primary key auto_increment not null,
                       genre varchar(255) not null
);

create table book (
                      id int primary key auto_increment not null,
                      cover varchar(500),
                      title varchar(255) not null,
                      publisher varchar(255) not null,
                      publish_date date not null,
                      page_count int not null,
                      description varchar(4000) ,
                      total_amount int not null,
                      remaining_amount int not null,
                      ISBN varchar(13) not null,
                      status bool not null
);

create table book_genre (
                            book_id int not null,
                            genre_id int not null,
                            foreign key (book_id) references book (id)  on delete cascade,
                            foreign key (genre_id) references genre (id)  on delete cascade
);

create table book_author (
                             book_id int not null,
                             author_id int not null,
                             foreign key (book_id) references book (id)  on delete cascade,
                             foreign key (author_id) references author (id)  on delete cascade
);

create table book_order (
                            id int primary key auto_increment not null,
                            book_id int not null,
                            reader_id int not null,
                            borrow_date timestamp not null,
                            borrow_period enum('ONE','TWO','THREE','SIX','TWELVE') not null ,
                            status enum( 'ORDERED','RETURNED','RETURNED_AND_DAMAGED', 'LOST'),
                            comment varchar(255),
                            due_date date not null,
                            return_date date ,
                            foreign key (book_id) references book (id)  on delete cascade,
                            foreign key (reader_id) references reader (id)  on delete cascade
);