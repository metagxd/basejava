package com.basejava.webapp.storage;

import com.basejava.webapp.model.Resume;

import java.util.Arrays;

public class SortedArrayStorage extends AbstractArrayStorage {

    @Override
    protected int getResumeIndex(String uuid) {
        Resume searchKey = new Resume();
        searchKey.setUuid(uuid);
        return Arrays.binarySearch(storage, 0, size, searchKey);
    }

    @Override
    protected void doSave(Resume resume) {
        for (int i = size; i >= Math.abs(index) - 1; i--) {
            storage[i + 1] = storage[i];
        }
        storage[Math.abs(index) - 1] = resume;
    }
}

