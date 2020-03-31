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
        int index = getResumeIndex(resume.getUuid());
        if (index >= 0) {
            throw new ExistStorageException(resume.getUuid());
        }
        doSave(index, resume);
    }

    public void update(Resume resume) {
        checkExist(resume.getUuid());
        doUpdate(index, resume);
    }

    public Resume get(String uuid) {
        checkExist(uuid);
        return doGet(index);
    }

    public void delete(String uuid) {
        checkExist(uuid);
        doDelete(index);
    }

    protected abstract int getResumeIndex(String uuid);

    protected abstract void doSave(int index, Resume resume);

    protected abstract void doUpdate(int index, Resume resume);

    protected abstract Resume doGet(int index);

    protected abstract void doDelete(int index);

    private void checkExist(String uuid) {
        index = getResumeIndex(uuid);
        if (index < 0) {
            throw new NotExistStorageException(uuid);
        }
    }

}
