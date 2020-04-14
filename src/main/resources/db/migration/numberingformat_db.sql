create sequence sq_nfm_numbering_format start with 1 increment by 1
create sequence sq_nfm_reserved_number_interval start with 1 increment by 1
create table tb_nfm_numbering_format
(
    numbering_format_id   number(19, 0)      not null,
    created_by            varchar2(200 char),
    created_date          timestamp          not null,
    last_modified_by      varchar2(200 char),
    last_modified_date    timestamp          not null,
    last_allocated_serial number(19, 0),
    numbering_format      varchar2(400 char) not null,
    numbering_usage       varchar2(50 char)  not null,
    start_at              number(19, 0)      not null check (start_at >= 1),
    primary key (numbering_format_id)
)
create table tb_nfm_reserved_number_interval
(
    id                  number(19, 0) not null,
    created_by          varchar2(200 char),
    created_date        timestamp     not null,
    last_modified_by    varchar2(200 char),
    last_modified_date  timestamp     not null,
    reserved_end        number(19, 0),
    reserved_start      number(19, 0) check (reserved_start >= 1),
    numbering_format_id number(19, 0) not null,
    primary key (id)
)
alter table tb_nfm_numbering_format
    add constraint UKa4jmm8iofpcfqhogngun7wa3u unique (numbering_usage, numbering_format)
alter table tb_nfm_reserved_number_interval
    add constraint FK2r4pp1f1pqisyia6gryo9pfjl foreign key (numbering_format_id) references tb_nfm_numbering_format
