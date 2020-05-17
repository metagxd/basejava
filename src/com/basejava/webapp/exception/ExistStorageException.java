package com.basejava.webapp.exception;

public class ExistStorageException extends StorageException {
    public ExistStorageException(String uuid) {
        super("Resume " + uuid + " already exist", uuid);
    }

    public ExistStorageException(String uuid, Exception e) {
        super("Resume " + uuid + " already exist", uuid, e);
    }
}
