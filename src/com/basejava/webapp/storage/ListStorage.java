package com.basejava.webapp.storage;

import com.basejava.webapp.model.Resume;

import java.util.ArrayList;

public class ListStorage extends AbstractStorage {
    protected ArrayList<Resume> list = new ArrayList<>();

    public int size() {
        return list.size();
    }

    public void clear() {
        list.clear();
    }

    public Resume doGet(int index) {
        return list.get(index);
    }

    protected void doDelete(int index) {
        list.remove(index);
        list.trimToSize();
    }

    @Override
    public Resume[] getAll() {
        Resume[] resumes = new Resume[list.size()];
        resumes = list.toArray(resumes);
        return resumes;
    }

    protected int getResumeIndex(String uuid) {
        Resume searchKey = new Resume(uuid);
        return list.indexOf(searchKey);
    }

    protected void doSave(int index, Resume resume) {
        list.add(resume);
    }

    @Override
    protected void doUpdate(int index, Resume resume) {
        list.set(index, resume);
    }
}
