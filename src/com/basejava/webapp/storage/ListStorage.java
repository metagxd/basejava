package com.basejava.webapp.storage;

import com.basejava.webapp.model.Resume;

import java.util.ArrayList;
import java.util.List;

public class ListStorage extends AbstractStorage<Integer> {
    private List<Resume> list = new ArrayList<>();

    public int size() {
        return list.size();
    }

    public void clear() {
        list.clear();
    }

    @Override
    public Resume doGet(Integer searchKey) {
        return list.get(searchKey);
    }

    @Override
    protected void doDelete(Integer searchKey) {
        int index = searchKey;
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
    protected boolean isExist(Integer index) {
        return index >= 0;
    }

    @Override
    protected void doSave(Resume resume, Integer searchKey) {
        list.add(resume);
    }

    @Override
    protected void doUpdate(Resume resume, Integer searchKey) {
        int index = searchKey;
        list.set(index, resume);
    }
}
