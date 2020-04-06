package com.basejava.webapp.storage;

import com.basejava.webapp.model.Resume;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class UuidMapStorage extends AbstractStorage {
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
    protected String getResumeSearchKey(String uuid) {
        return uuid;
    }

    @Override
    protected boolean isExist(Object searchKey) {
        String uuid = (String) searchKey;
        return map.containsKey(uuid);
    }

    @Override
    protected void doSave(Resume resume, Object searchKey) {
        map.put(getStringSearchKey(searchKey), resume);
    }

    @Override
    protected void doUpdate(Resume resume, Object searchKey) {
        doSave(resume, searchKey);
    }

    @Override
    protected Resume doGet(Object searchKey) {
        return map.get(getStringSearchKey(searchKey));
    }

    @Override
    protected void doDelete(Object searchKey) {
        map.remove(getStringSearchKey(searchKey));
    }

    private String getStringSearchKey(Object searchKey) {
        return (String) searchKey;
    }
}
