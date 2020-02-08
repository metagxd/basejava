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
        size++;
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
        if (uuid == null) {
            System.out.println("Error!");
            return;
        }
        int pos = 0;
        boolean isDeletionOk = false;
        while (pos < size()) {
            if (storage[pos].getUuid().equals(uuid)) {
                storage[pos] = null;
                isDeletionOk = true;
                break;
            }
            pos++;
        }
        if (!isDeletionOk) {
            System.out.println("Error!");
            return;
        }
        System.arraycopy(storage,pos+1,storage,pos,size-pos-1);
            storage[size] = null;
            size--;
    }

    /**
     * @return array, contains only Resumes in storage (without null)
     */
    public Resume[] getAll() {
        return Arrays.copyOf(storage, size());
    }

    public int size() {
        return size;
    }
}
