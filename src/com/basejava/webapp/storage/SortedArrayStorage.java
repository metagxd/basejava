package com.basejava.webapp.storage;

import com.basejava.webapp.model.Resume;

import java.util.Arrays;

public class SortedArrayStorage extends AbstractArrayStorage{
    @Override
    public void clear() {

    }

    @Override
    public void save(Resume resume) {
        index = getResumeIndex(resume.getUuid());
        if (size == 0) {
            storage[0] = resume;
            size++;
            return;
        } else if (index == (-size - 1)) {
            storage[size] = resume;
            size++;
            return;
        } else if (index > 0) {
            System.out.println("ERROR: Resume " + resume.getUuid() + " already exist!");
            return;
        } else if (index == -1) {
            System.arraycopy(storage, 0, storage, 1, size + 1);
            storage[0] = resume;
            return;
        }
    }

    @Override
    public void update(Resume resume) {

    }

    @Override
    public Resume get(String uuid) {
        return null;
    }

    @Override
    public void delete(String uuid) {

    }

    @Override
    public int getResumeIndex(String uuid) {
        Resume searchKey = new Resume();
        searchKey.setUuid(uuid);
        return Arrays.binarySearch(storage, 0, size, searchKey);
    }
}

