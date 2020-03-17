create sequence hibernate_sequence start with 1 increment by 1;
create table tb_nfm_numbering_format
(
    id                    number(19, 0)      not null,
    created_by            varchar2(200 char),
    created_date          timestamp,
    last_allocated_serial number(19, 0),
    last_modified_by      varchar2(200 char),
    last_modified_date    timestamp,
    numbering_format      varchar2(400 char) not null,
    numbering_usage       varchar2(50 char)  not null,
    start_at              number(19, 0)      not null check (start_at >= 1),
    primary key (id)
);
create table tb_nfm_reserved_number_interval
(
    id                 number(19, 0)      not null,
    created_by         varchar2(200 char),
    created_date       timestamp,
    reserved_end       number(19, 0),
    last_modified_by   varchar2(200 char),
    last_modified_date timestamp,
    numbering_format   varchar2(400 char) not null,
    numbering_usage    varchar2(50 char)  not null,
    reserved_start     number(19, 0),
    primary key (id)
);
alter table tb_nfm_numbering_format
    add constraint UKa4jmm8iofpcfqhogngun7wa3u unique (numbering_usage, numbering_format)
