create sequence hibernate_sequence start with 1 increment by 1;
create table tb_nfm_numbering_format
(
    numbering_format      varchar2(255 char) not null,
    numbering_usage       varchar2(255 char) not null,
    id                    number(19, 0)      not null,
    created_by            varchar2(200 char),
    created_date          timestamp,
    last_allocated_serial number(19, 0),
    last_modified_by      varchar2(200 char),
    last_modified_date    timestamp,
    start_at              number(19, 0)      not null check (start_at >= 1),
    primary key (numbering_format, numbering_usage, id)
);
