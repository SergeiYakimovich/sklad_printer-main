-- apply alter tables
alter table print_job add column id uuid;
alter table print_job add constraint pk_print_job_id primary key (id);
