package com.basejava.webapp.storage;

import com.basejava.webapp.exception.ExistStorageException;
import com.basejava.webapp.exception.NotExistStorageException;
import com.basejava.webapp.exception.StorageException;
import com.basejava.webapp.model.Resume;

public abstract class AbstractStorage implements Storage {
    protected int index;

    public void save(Resume resume) {
        if (resume.getUuid() == null) {
            throw new StorageException("Null uuid not allowed!", resume.getUuid());
        }
        index = getResumeIndex(resume.getUuid());
        if (index >= 0) {
            throw new ExistStorageException(resume.getUuid());
        }
        doSave(resume);
    }

    public void update(Resume resume) {
        checkExist(resume.getUuid());
        doUpdate(resume);
    }

    public Resume get(String uuid) {
        checkExist(uuid);
        return doGet(uuid);
    }

    public void delete(String uuid) {
        checkExist(uuid);
        doDelete(uuid);
    }

    protected abstract int getResumeIndex(String uuid);

    protected abstract void doSave(Resume resume);

    protected abstract void doUpdate(Resume resume);

    protected abstract Resume doGet(String uuid);

    protected abstract void doDelete(String uuid);

    private void checkExist(String uuid) {
        index = getResumeIndex(uuid);
        if (index < 0) {
            throw new NotExistStorageException(uuid);
        }
    }

}
