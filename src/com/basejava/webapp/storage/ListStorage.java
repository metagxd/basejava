package com.basejava.webapp.storage;

import com.basejava.webapp.model.Resume;

import java.util.ArrayList;
import java.util.List;

public class ListStorage extends AbstractStorage {
    private List<Resume> list = new ArrayList<>();

    public int size() {
        return list.size();
    }

    public void clear() {
        list.clear();
    }

    @Override
    public Resume doGet(Object searchKey) {
        return list.get((Integer) searchKey);
    }

    @Override
    protected void doDelete(Object searchKey) {
        int index = (Integer) searchKey;
        list.remove(index);
    }

    @Override
    public List<Resume> getListOfResumes() {
        return this.list;
    }

    @Override
    protected Integer getResumeSearchKey(String uuid) {
        return list.indexOf(new Resume(uuid, "name"));
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
        int index = (Integer) searchKey;
        list.set(index, resume);
    }
}
