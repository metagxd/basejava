package com.basejava.webapp.storage;

import com.basejava.webapp.exception.ExistStorageException;
import com.basejava.webapp.exception.NotExistStorageException;
import com.basejava.webapp.exception.StorageException;
import com.basejava.webapp.model.Resume;

import java.util.ArrayList;

public class ListStorage extends AbstractStorage {
    ArrayList<Resume> list = new ArrayList<>();

    @Override
    public int size() {
        return list.size();
    }

    @Override
    public void clear() {
        list.clear();
    }

    @Override
    public void save(Resume resume) {
        if (resume.getUuid() == null) {
            throw new StorageException("Null uuid not allowed!", resume.getUuid());
        }
        if (list.contains(resume)) {
            throw new ExistStorageException(resume.getUuid());
        }
        list.add(resume);
    }

    @Override
    public void update(Resume resume) {
        int index = list.indexOf(resume);
        if (index == -1) {
            throw new NotExistStorageException(resume.getUuid());
        }
        list.set(index, resume);
    }

    @Override
    public Resume get(String uuid) {
        return list.get(getIndex(uuid));
    }

    @Override
    public void delete(String uuid) {
        list.remove(getIndex(uuid));
        list.trimToSize();
    }

    @Override
    public Resume[] getAll() {
        Resume[] resumes = new Resume[list.size()];
        resumes = list.toArray(resumes);
        return resumes;
    }

    protected int getIndex(String uuid) {
        Resume r = new Resume(uuid);
        int index = list.indexOf(r);
        if (index == -1) {
            throw new NotExistStorageException(r.getUuid());
        }
        return index;
    }
}
