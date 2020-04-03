package com.basejava.webapp.storage;

import com.basejava.webapp.model.Resume;

import java.util.Arrays;

public class SortedArrayStorage extends AbstractArrayStorage {

    protected Object getResumeSearchKey(String uuid) {
        Resume searchingResume = new Resume(uuid);
        return Arrays.binarySearch(storage, 0, size, searchingResume);
    }

    protected void addResume(int index, Resume resume) {
        int position = -index - 1;
        System.arraycopy(storage, position, storage, position + 1, size - position);
        storage[position] = resume;
    }

    protected void deleteResume(int index) {
        System.arraycopy(storage, index + 1, storage, index, size - index - 1);
    }
}