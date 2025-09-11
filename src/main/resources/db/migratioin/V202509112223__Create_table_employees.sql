create table employees
(
    id         bigint auto_increment
        primary key,
    age        int          not null,
    company_id bigint       null,
    gender     varchar(255) null,
    name       varchar(255) null,
    salary     double       not null,
    status     bit          not null,
    constraint FK1ekpcbo0lmdx6ou8e3fh9j4lq
        foreign key (company_id) references companies (id)
);


