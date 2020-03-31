package com.basejava.webapp.storage;

import com.basejava.webapp.exception.StorageException;
import com.basejava.webapp.model.Resume;

import java.util.Arrays;

/**
 * Array based storage for Resumes
 */
public abstract class AbstractArrayStorage extends AbstractStorage {
    protected static final int STORAGE_LIMIT = 10_000;

    protected Resume[] storage = new Resume[STORAGE_LIMIT];
    protected int size;

    public int size() {
        return size;
    }

    public void clear() {
        Arrays.fill(storage, 0, size, null);
        size = 0;
    }

    public Resume[] getAll() {
        return Arrays.copyOf(storage, size);
    }

    protected void doUpdate(int index, Resume resume) {
        storage[index] = resume;
    }

    protected void doSave(int index, Resume resume) {
        if (size == STORAGE_LIMIT) {
            throw new StorageException("Storage overflow!", "");
        }
        addResume(index, resume);
        size++;
    }

    protected Resume doGet(int index) {
        return storage[index];
    }

    protected void doDelete(int index) {
        deleteResume(index);
        storage[size - 1] = null;
        size--;
    }

    protected abstract void deleteResume(int index);

    protected abstract void addResume(int index, Resume resume);
}
