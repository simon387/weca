create table messageentity
(
    id        bigint   auto_increment    not null
        primary key,
    ip        varchar(255) null,
    message   varchar(255) null,
    timestamp datetime(6)  null
);

