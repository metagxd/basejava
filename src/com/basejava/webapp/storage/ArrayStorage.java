package com.basejava.webapp.storage;

import com.basejava.webapp.model.Resume;

import java.util.Arrays;

/**
 * Array based storage for Resumes
 */
public class ArrayStorage {
    private Resume[] storage = new Resume[10_000];
    private int size;
    private int index;

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
        if (index != -1) {
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
        index = getResumeIndex(resume.getUuid());
        if (index == -1) {
            System.out.println("ERROR: Resume " + resume.getUuid() + " doesn't exist!");
            return;
        }
        storage[index] = resume;
    }

    public Resume get(String uuid) {
        index = getResumeIndex(uuid);
        if (index == -1) {
            System.out.println("ERROR: Resume " + uuid + " doesn't exist!");
            return null;
        }
        return storage[index];
    }

    public void delete(String uuid) {
        index = getResumeIndex(uuid);
        if (index == -1) {
            System.out.println("ERROR: Resume " + uuid + " doesn't exist!");
            return;
        }

        System.arraycopy(storage, index + 1, storage, index, size - index - 1);
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

    private int getResumeIndex(String uuid) {   //return -1 if resume doesn't exist
        for (int i = 0; i < size; i++) {
            if (storage[i].getUuid().equals(uuid)) {
                return i;
            }
        }
        return -1;
    }
}
