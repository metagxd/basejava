package com.basejava.webapp.storage;

import com.basejava.webapp.model.Resume;

/**
 * Array based storage for Resumes
 */
public class ArrayStorage extends AbstractArrayStorage {

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
        if (size == STORAGE_LIMIT) {
            System.out.println("ERROR: Storage overflow!");
            return;
        }
        storage[size] = resume;
        size++;
    }

    protected int getResumeIndex(String uuid) {   //return -1 if resume doesn't exist
        for (int i = 0; i < size; i++) {
            if (storage[i].getUuid().equals(uuid)) {
                return i;
            }
        }
        return -1;
    }
}
