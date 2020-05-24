CREATE TABLE resume
(
    uuid      TEXT NOT NULL
        CONSTRAINT resume_pkey
            PRIMARY KEY,
    full_name TEXT NOT NULL
);

ALTER TABLE resume
    OWNER TO postgres;

CREATE TABLE contact
(
    id          SERIAL NOT NULL
        CONSTRAINT contact_pk
            PRIMARY KEY,
    type        TEXT   NOT NULL,
    value       TEXT   NOT NULL,
    resume_uuid TEXT   NOT NULL
        CONSTRAINT contact_resume_uuid_fk
            REFERENCES resume
            ON DELETE CASCADE
);

CREATE UNIQUE INDEX contact_uuid_type_index
    ON contact (resume_uuid, type);

ALTER TABLE contact
    OWNER TO postgres;

CREATE TABLE section
(
    section_type  TEXT   NOT NULL,
    section_value TEXT,
    id            SERIAL NOT NULL
        CONSTRAINT sections_pk
            PRIMARY KEY,
    resume_uuid   TEXT   NOT NULL
        CONSTRAINT sections_resume_uuid_fk
            REFERENCES resume
            ON DELETE CASCADE
);

ALTER TABLE section
    OWNER TO postgres;
