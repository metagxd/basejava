package com.basejava.webapp.storage;

import com.basejava.webapp.model.Resume;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class ResumeMapStorage extends AbstractStorage<Resume> {
    private HashMap<String, Resume> map = new HashMap<>();

    public int size() {
        return map.size();
    }

    public void clear() {
        map.clear();
    }

    @Override
    public List<Resume> getListOfResumes() {
        return new ArrayList<>(map.values());
    }

    @Override
    protected Resume getResumeSearchKey(String uuid) {
        return map.get(uuid);
    }

    @Override
    protected boolean isExist(Resume searchKey) {
        return searchKey != null;
    }

    @Override
    protected void doSave(Resume resume, Resume searchKey) {
        map.put(resume.getUuid(), resume);
    }

    @Override
    protected void doUpdate(Resume resume, Resume searchKey) {
        doSave(resume, resume);
    }

    @Override
    protected Resume doGet(Resume searchKey) {
        return map.get(getUUIDFromSearchKey(searchKey));
    }

    @Override
    protected void doDelete(Resume searchKey) {
        map.remove(getUUIDFromSearchKey(searchKey));
    }

    private String getUUIDFromSearchKey(Resume searchKey) {
        return searchKey.getUuid();
    }
}
