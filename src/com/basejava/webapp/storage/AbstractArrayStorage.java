package com.basejava.webapp.storage;

import com.basejava.webapp.model.Resume;

import java.util.Arrays;

/**
 * Array based storage for Resumes
 */
public abstract class AbstractArrayStorage implements Storage {
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

    public void save(Resume resume) {
        if (resume.getUuid() == null) {
            System.out.println("ERROR: null uuid not allowed!");
            return;
        }
        index = getResumeIndex(resume.getUuid());
        if (index >= 0) {
            System.out.println("ERROR: Resume " + resume.getUuid() + " already exist!");
            return;
        }
        if (size == STORAGE_LIMIT) {
            System.out.println("ERROR: Storage overflow!");
            return;
        }
        doSave(resume);
        size++;
    }

    public void update(Resume resume) {
        index = getResumeIndex(resume.getUuid());
        if (index < 0) {
            System.out.println("ERROR: Resume " + resume.getUuid() + " doesn't exist!");
            return;
        }
        storage[index] = resume;
    }

    public Resume get(String uuid) {
        index = getResumeIndex(uuid);
        if (index < 0) {
            System.out.println("ERROR: Resume " + uuid + " doesn't exist!");
            return null;
        }
        return storage[index];
    }

    public void delete(String uuid) {
        index = getResumeIndex(uuid);
        if (index < 0) {
            System.out.println("ERROR: Resume " + uuid + " doesn't exist!");
            return;
        }
        doDelete(index);
        storage[size - 1] = null;
        size--;
    }

    /**
     * @return array, contains only Resumes in storage (without null)
     */
    public Resume[] getAll() {
        return Arrays.copyOf(storage, size);
    }

    protected abstract int getResumeIndex(String uuid);

    protected abstract void doSave(Resume resume);

    protected abstract void doDelete(int index);
}
