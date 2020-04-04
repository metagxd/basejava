package com.basejava.webapp.storage;

import com.basejava.webapp.model.Resume;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class ListStorage extends AbstractStorage {
    private ArrayList<Resume> list = new ArrayList<>();

    public int size() {
        return list.size();
    }

    public void clear() {
        list.clear();
    }

    @Override
    public Resume doGet(Object searchKey) {
        return list.get(getIntSearchKey(searchKey));
    }

    @Override
    protected void doDelete(Object searchKey) {
        int index = getIntSearchKey(searchKey);
        list.remove(index);
        list.trimToSize();
    }

    @Override
    public Resume[] getAll() {
        Resume[] resumes = new Resume[list.size()];
        return list.toArray(resumes);
    }

    @Override
    public List<Resume> getAllSorted() {
        ArrayList<Resume> resumes = this.list;
        Collections.sort(resumes);
        return resumes;
    }

    @Override
    protected Integer getResumeSearchKey(String uuid) {
        return list.indexOf(new Resume(uuid));
    }

    @Override
    protected boolean isExist(Object index) {
        return ((Integer) index) >= 0;
    }

    @Override
    protected void doSave(Resume resume, Object searchKey) {
        list.add(resume);
    }

    @Override
    protected void doUpdate(Resume resume, Object searchKey) {
        int index = getIntSearchKey(searchKey);
        list.set(index, resume);
    }
}
