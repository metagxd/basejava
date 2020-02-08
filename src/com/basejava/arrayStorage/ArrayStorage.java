package com.basejava.arrayStorage;

import java.util.Arrays;

/**
 * Array based storage for Resumes
 */
public class ArrayStorage {
    Resume[] storage = new Resume[10000];
    private int size;

    public void clear() {
        for (int i = 0; i < size(); i++) {
            storage[i] = null;
        }
        size = 0;
    }

    public void save(Resume resume) {
        storage[size()] = resume;
        size += 1;
    }

    public Resume get(String uuid) {
        int i = 0;

        while (i < size()) {
            if (storage[i].getUuid().equals(uuid)) {
                return storage[i];
            }
            i++;
        }
        return null;
    }

    public void delete(String uuid) {
        int pos = 0;
        for (int i = 0; i < size(); i++) {
            if (storage[i].getUuid().equals(uuid)) {
                storage[i] = null;
                pos = i;
                break;
            }
        }
        for (int i = pos; i < size - 1; i++) {
            storage[i] = storage[i + 1];
        }
        storage[size] = null;
        size -= 1;
    }

    /**
     * @return array, contains only Resumes in storage (without null)
     */
    public Resume[] getAll() {
        return Arrays.copyOf(storage, size());
    }

    public int size() {
        if (size != 0) {
            return size;
        }
        for (Resume resume : storage) {
            if (resume == null) {
                break;
            }
            size++;
        }
        return size;
    }
}
