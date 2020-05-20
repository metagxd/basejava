package com.basejava.webapp.sql;

import com.basejava.webapp.exception.ExistStorageException;
import com.basejava.webapp.exception.StorageException;

import java.sql.SQLException;

public class ExceptionUtil {

    public static StorageException convertException(SQLException e) {
        if (e.getSQLState().equals("23505")) {
            return new ExistStorageException(e);
        }
        return new StorageException("Process error, state: " + e.getSQLState(), e);
    }
}
