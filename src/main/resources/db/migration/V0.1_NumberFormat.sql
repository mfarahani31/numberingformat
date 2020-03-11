create sequence hibernate_sequence start with 1 increment by 1;
create table tb_nfm_numbering_format
(
    id                    number(19, 0) not null,
    created_by            varchar2(200 char),
    created_date          timestamp,
    last_allocated_serial number(19, 0),
    last_modified_by      varchar2(200 char),
    last_modified_date    timestamp,
    numbering_format      varchar2(400 char),
    numbering_usage       varchar2(50 char),
    start_at              number(19, 0),
    primary key (id)
);
alter table tb_nfm_numbering_format
    add constraint UK_a93452jchv7giiph2i5gqfkrs unique (numbering_format);
alter table tb_nfm_numbering_format
    add constraint UK_sxh1va92qb3w7ss2nmhesougx unique (numbering_usage);