package com.basejava.webapp.storage;

import com.basejava.webapp.model.Resume;

import java.util.ArrayList;

public class ListStorage extends AbstractStorage {
    private ArrayList<Resume> list = new ArrayList<>();

    public int size() {
        return list.size();
    }

    public void clear() {
        list.clear();
    }

    public Resume doGet(Object searchKey) {
        return list.get(getIntSearchKey(searchKey));
    }

    protected void doDelete(Object searchKey) {
        int index = getIntSearchKey(searchKey);
        list.remove(index);
        list.trimToSize();
    }

    public Resume[] getAll() {
        Resume[] resumes = new Resume[list.size()];
        return list.toArray(resumes);
    }

    protected Object getResumeSearchKey(String uuid) {
        return list.indexOf(new Resume(uuid));
    }

    protected boolean isExist(String uuid) {
        return list.contains(new Resume(uuid));
    }

    protected void doSave(Resume resume, Object searchKey) {
        list.add(resume);
    }

    protected void doUpdate(Resume resume, Object searchKey) {
        int index = getIntSearchKey(searchKey);
        list.set(index, resume);
    }
}
