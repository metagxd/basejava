package com.basejava.webapp.storage;

import com.basejava.webapp.model.Resume;

/**
 * Array based storage for Resumes
 */
public class ArrayStorage extends AbstractArrayStorage {

    protected int getResumeIndex(String uuid) { // return -1 if resume doesn't exist
        for (int i = 0; i < size; i++) {
            if (storage[i].getUuid().equals(uuid)) {
                return i;
            }
        }
        return -1;
    }

    protected void addResume(int index, Resume resume) {
        storage[size] = resume;
    }

    protected void deleteResume(int index) {
        storage[index] = storage[size - 1];
    }

    ;
}
