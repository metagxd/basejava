package com.basejava.webapp.storage;

import com.basejava.webapp.model.Resume;

import java.util.Arrays;

/**
 * Array based storage for Resumes
 */
public class ArrayStorage {
    private Resume[] storage = new Resume[10_000];
    private int size;

    public void clear() {
        Arrays.fill(storage, 0, size, null);
        size = 0;
    }

    public void save(Resume resume) {
        if (isResumeExist(resume.getUuid())) {
            System.out.println("ERROR: Resume " + resume.getUuid() + " already exist!");
            return;
        }
        if (size == storage.length) {
            System.out.println("ERROR: Storage overflow!");
            return;
        }
        storage[size] = resume;
        size++;
    }

    public void update(Resume resume) {
        if (!isResumeExist(resume.getUuid())) {
            System.out.println("ERROR: Resume " + resume.getUuid() + " doesn't exist!");
            return;
        }
        for (int i = 0; i < size; i++) {
            if (storage[i].getUuid().equals(resume.getUuid())) {
                storage[i] = resume;
            }
        }
    }

    public Resume get(String uuid) {
        if (!isResumeExist(uuid)) {
            System.out.println("ERROR: Resume " + uuid + " doesn't exist!");
            return null;
        }

        for (int i = 0; i < size; i++) {
            if (storage[i].getUuid().equals(uuid)) {
                return storage[i];
            }
        }
        return null;
    }

    public void delete(String uuid) {
        if (!isResumeExist(uuid)) {
            System.out.println("ERROR: Resume " + uuid + " doesn't exist!");
            return;
        }

        int pos = 0;
        while (pos < size) {
            if (storage[pos].getUuid().equals(uuid)) {
                break;
            }
            pos++;
        }

        System.arraycopy(storage, pos + 1, storage, pos, size - pos - 1);
        storage[size] = null;
        size--;
    }

    /**
     * @return array, contains only Resumes in storage (without null)
     */
    public Resume[] getAll() {
        return Arrays.copyOf(storage, size);
    }

    public int size() {
        return size;
    }

    private boolean isResumeExist(String uuid) {
        for (int i = 0; i < size; i++) {
            if (storage[i].getUuid().equals(uuid)) {
                return true;
            }
        }
        return false;
    }
}
