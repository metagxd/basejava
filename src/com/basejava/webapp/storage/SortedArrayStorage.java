package com.basejava.webapp.storage;

import com.basejava.webapp.model.Resume;

import java.util.Arrays;

public class SortedArrayStorage extends AbstractArrayStorage {

    protected int getResumeIndex(String uuid) {
        Resume searchKey = new Resume();
        searchKey.setUuid(uuid);
        return Arrays.binarySearch(storage, 0, size, searchKey);
    }

    protected void doSave(Resume resume) {
        int position = -index - 1;
    //TODO chande to arraycopy
        for (int i = size; i >= position; i--) {
            storage[i + 1] = storage[i];
        }
        storage[position] = resume;
    }

    protected void doDelete(int index) {
        System.arraycopy(storage, index + 1, storage, index, size - index - 1);
    }
}