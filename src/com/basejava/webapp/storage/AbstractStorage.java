package com.basejava.webapp.storage;

import com.basejava.webapp.exception.ExistStorageException;
import com.basejava.webapp.exception.NotExistStorageException;
import com.basejava.webapp.exception.StorageException;
import com.basejava.webapp.model.Resume;

import java.util.Collections;
import java.util.List;

public abstract class AbstractStorage implements Storage {
    protected Object searchKey;

    protected static int getIntSearchKey(Object searchKey) {
        return (Integer) searchKey;
    }

    public void save(Resume resume) {
        if (resume.getUuid() == null) {
            throw new StorageException("Null uuid not allowed!", resume.getUuid());
        }
        searchKey = getNotExistSearchKey(resume.getUuid());
        doSave(resume, searchKey);
    }

    public void update(Resume resume) {
        searchKey = getExistSearchKey(resume.getUuid());
        doUpdate(resume, searchKey);
    }

    public Resume get(String uuid) {
        searchKey = getExistSearchKey(uuid);
        return doGet(searchKey);
    }

    public void delete(String uuid) {
        searchKey = getExistSearchKey(uuid);
        doDelete(searchKey);
    }

    private Object getExistSearchKey(String uuid) {
        searchKey = getResumeSearchKey(uuid);
        if (!isExist(searchKey)) {
            throw new NotExistStorageException(uuid);
        }
        return searchKey;
    }

    private Object getNotExistSearchKey(String uuid) {
        searchKey = getResumeSearchKey(uuid);
        if (isExist(searchKey)) {
            throw new ExistStorageException(uuid);
        }
        return searchKey;
    }

    @Override
    public List<Resume> getAllSorted() {
        List<Resume> listOfResumes = getListOfResumes();
        Collections.sort(listOfResumes);
        return listOfResumes;
    }

    protected abstract List<Resume> getListOfResumes();

    protected abstract Object getResumeSearchKey(String uuid);

    protected abstract boolean isExist(Object searchKey);

    protected abstract void doSave(Resume resume, Object searchKey);

    protected abstract void doUpdate(Resume resume, Object searchKey);

    protected abstract Resume doGet(Object searchKey);

    protected abstract void doDelete(Object searchKey);

}
