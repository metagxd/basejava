package com.basejava.webapp.exception;

import java.sql.SQLException;

public class ExistStorageException extends StorageException {
    public ExistStorageException(String uuid) {
        super("Resume " + uuid + " already exist", uuid);
    }

    public ExistStorageException(SQLException e) {
        super(e.getMessage() + " State: " + e.getSQLState(), e);
    }
}
