package com.basejava.webapp.storage;

import com.basejava.webapp.model.Resume;

import java.util.Arrays;

public class SortedArrayStorage extends AbstractArrayStorage {

    @Override
    public void save(Resume resume) {
        if (resume.getUuid() == null) {
            System.out.println("ERROR: null uuid not allowed!");
            return;
        }
        if (size == STORAGE_LIMIT) {
            System.out.println("ERROR: Storage overflow!");
            return;
        }
        if (size == 0) {
            storage[0] = resume;
            size++;
            return;
        }

        index = getResumeIndex(resume.getUuid());
        if (index >= 0) {
            System.out.println("ERROR: Resume " + resume.getUuid() + " already exist!");
        } else {
            for (int i = size; i >= Math.abs(index) - 1; i--) {
                storage[i + 1] = storage[i];
            }
            storage[Math.abs(index) - 1] = resume;
            size++;
        }
    }

    @Override
    public int getResumeIndex(String uuid) {
        Resume searchKey = new Resume();
        searchKey.setUuid(uuid);
        return Arrays.binarySearch(storage, 0, size, searchKey);
    }
}

