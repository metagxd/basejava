package com.basejava.webapp.storage;

import java.util.Arrays;
import com.basejava.webapp.model.Resume;

/**
 * Array based storage for Resumes
 */
public abstract class AbstractArrayStorage implements Storage {
    protected static final int STORAGE_LIMIT = 10_000;

    protected Resume[] storage = new Resume[STORAGE_LIMIT];
    protected int size;
    protected int index;

    public int size() {
        return size;
    }

     public void clear() {
        Arrays.fill(storage, 0, size, null);
        size = 0;
    }

        /**
     * @return array, contains only Resumes in storage (without null)
     */
    public Resume[] getAll() {
        return Arrays.copyOf(storage, size);
    }
    
    protected abstract int getResumeIndex(String uuid);
}
