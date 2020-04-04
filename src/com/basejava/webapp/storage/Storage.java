package com.basejava.webapp.storage;

import com.basejava.webapp.model.Resume;

import java.util.List;

/**
 * Array based storage for Resumes
 */
public interface Storage {

    int size();

    void clear();

    void save(Resume resume);

    void update(Resume resume);

    Resume get(String uuid);

    void delete(String uuid);

    /**
     * @return array, contains only Resumes in storage (without null)
     */
    Resume[] getAll();
    /**
     * @return list of sorted resumes
     */
    List<Resume> getAllSorted();
}
