package com.basejava.arrayStorage;

import java.util.Arrays;

/**
 * Array based storage for Resumes
 */
public class ArrayStorage {
    Resume[] storage = new Resume[10000];

    public void clear() {
        for (int i = 0; i < size(); i++) {
            storage[i] = null;
        }
    }

    public void save(Resume resume) {
        storage[size()] = resume;
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
        int size = size();
        for (int i = 0; i < size(); i++) {
            if (storage[i].getUuid().equals(uuid)) {
                storage[i] = null;
                pos = i;
                break;
            }
        }
        for (int i = pos; i < size; i++) {
            storage[i] = storage[i + 1];
        }
        storage[size] = null;
    }

    /**
     * @return array, contains only Resumes in storage (without null)
     */
    public Resume[] getAll() {
        return Arrays.copyOf(storage, size());
    }

    public int size() {
        int size = 0;
        for (int i = 0; i < storage.length; i++) {
            if (storage[i] == null) {
                break;
            }
            size++;
        }
        return size;
    }
}
