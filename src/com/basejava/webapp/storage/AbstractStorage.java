package com.basejava.webapp.storage;

import com.basejava.webapp.model.Resume;

public abstract class AbstractStorage implements Storage {


    public int size() {
        return 0;
    }

    public void clear() {

    }

    public void save(Resume resume) {

    }

    public void update(Resume resume) {

    }

    public Resume get(String uuid) {
        return null;
    }

    public void delete(String uuid) {

    }

    public Resume[] getAll() {
        return new Resume[0];
    }

}
