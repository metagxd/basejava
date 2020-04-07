package com.basejava.webapp.storage;

import com.basejava.webapp.exception.StorageException;
import com.basejava.webapp.model.Resume;

import java.util.Arrays;
import java.util.List;

/**
 * Array based storage for Resumes
 */
public abstract class AbstractArrayStorage extends AbstractStorage {
    protected static final int STORAGE_LIMIT = 10_000;

    protected Resume[] storage = new Resume[STORAGE_LIMIT];
    protected int size;
    protected int index;

    public int size() {
        return size;
    }

    public void clear() {
        Arrays.fill(storage, 0, size, null);
        size = 0;
    }

    @Override
    public List<Resume> getListOfResumes() {
        return Arrays.asList(Arrays.copyOf(storage, size));
    }

    protected void doUpdate(Resume resume, Object searchKey) {
        index = (Integer) searchKey;
        storage[index] = resume;

    }

    protected void doSave(Resume resume, Object searchKey) {
        if (size == STORAGE_LIMIT) {
            throw new StorageException("Storage overflow!", "");
        }
        index = (Integer) searchKey;
        addResume(index, resume);
        size++;
    }

    protected Resume doGet(Object searchKey) {
        index = (Integer) searchKey;
        return storage[index];
    }

    protected void doDelete(Object searchKey) {
        index = (Integer) searchKey;
        deleteResume(index);
        storage[size - 1] = null;
        size--;
    }


    protected boolean isExist(Object index) {
        return (Integer) index >= 0;
    }

    protected abstract void deleteResume(int index);

    protected abstract void addResume(int index, Resume resume);
}
