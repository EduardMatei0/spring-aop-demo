create schema if not exists aop;
alter database aop set search_path to 'aop';

CREATE TABLE if not exists "virtual_machines" (
                          "id" serial primary key,
                          "name" varchar   NOT NULL,
                          "azure_group_id" integer,
                          "uuid" varchar,
                          "size" integer,
                          "memory" integer,
                          "operating_system" varchar,
                          "created" timestamp   NOT NULL,
                          "updated" timestamp   NOT NULL
);

CREATE TABLE IF NOT EXISTS "audit_external" (
    "id" serial primary key,
    "method_name" varchar not null,
    "req_body" text,
    "response" text,
    "status_code" integer
);

CREATE TABLE if not exists "users" (
                                        "id" serial primary key,
                                        "name" varchar   NOT NULL,
                                        "email" varchar   NOT NULL,
                                        "phone_number" varchar,
                                        "created" timestamp   NOT NULL,
                                        "updated" timestamp   NOT NULL,
                                        CONSTRAINT "uc_people_email" UNIQUE (
                                                                             "email"
                                            )
);

CREATE TABLE if not exists "azure_groups" (
                                             "id" serial primary key,
                                             "name" varchar NOT NULL,
                                             CONSTRAINT "uc_departments_name" UNIQUE (
                                                                                      "name"
                                                 )
);

CREATE TABLE if not exists "user_groups" (
                                                    "user_id" integer  constraint fk_user_id references users,
                                                    "group_id" integer constraint fk_group_id references azure_groups
);
