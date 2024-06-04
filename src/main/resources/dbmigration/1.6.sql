-- drop dependencies
alter table auth_references drop constraint if exists ck_auth_references_entity;
-- apply post alter
alter table auth_references add constraint ck_auth_references_entity check ( entity in ('Vendor','Product','Lot','Counterparty','Contract','BankAccount','Store','Cell','User','Role','Host','Printer','PrintJobs'));
