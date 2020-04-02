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

    public Resume doGet(String uuid) {
        int index = getResumeIndex(uuid);
        return list.get(index);
    }

    protected void doDelete(String uuid) {
        list.remove(getResumeIndex(uuid));
        list.trimToSize();
    }

    @Override
    public Resume[] getAll() {
        Resume[] resumes = new Resume[list.size()];
        return list.toArray(resumes);
    }

    protected int getResumeIndex(String uuid) {
        Resume searchKey = new Resume(uuid);
        return list.indexOf(searchKey);
    }

    protected void doSave(Resume resume) {
        list.add(resume);
    }

    @Override
    protected void doUpdate(Resume resume) {
        list.set(index, resume);
    }
}
