CREATE TABLE resume
(
    uuid      CHAR(36) NOT NULL
        CONSTRAINT resume_pkey
            PRIMARY KEY,
    full_name TEXT     NOT NULL
);

ALTER TABLE resume
    OWNER TO postgres;

CREATE TABLE contact
(
    id          SERIAL   NOT NULL
        CONSTRAINT contact_pk
            PRIMARY KEY,
    type        TEXT     NOT NULL,
    value       TEXT     NOT NULL,
    resume_uuid CHAR(36) NOT NULL
        CONSTRAINT contact_resume_uuid_fk
            REFERENCES resume
            ON DELETE CASCADE
);

CREATE UNIQUE INDEX contact_uuid_type_index
    ON contact (resume_uuid, type);



ALTER TABLE contact
    OWNER TO postgres;

