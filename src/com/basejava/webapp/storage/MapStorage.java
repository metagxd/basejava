package com.basejava.webapp.storage;

import com.basejava.webapp.model.Resume;

import java.util.HashMap;

public class MapStorage extends AbstractStorage {
    private HashMap<String, Resume> map = new HashMap<>();

    public int size() {
        return map.size();
    }

    public void clear() {
        map.clear();
    }

    public Resume[] getAll() {
        Resume[] resumes = new Resume[map.size()];
        return map.values().toArray(resumes);
    }

    protected Object getResumeSearchKey(String uuid) {
        return uuid;
    }

    protected boolean isExist(String uuid) {
        return map.containsKey(uuid);
    }

    protected void doSave(Resume resume, Object searchKey) {
        map.put(getStringSearchKey(searchKey), resume);
    }

    protected void doUpdate(Resume resume, Object searchKey) {
        doSave(resume, searchKey);
    }

    protected Resume doGet(Object searchKey) {
        return map.get(getStringSearchKey(searchKey));
    }

    protected void doDelete(Object searchKey) {
        map.remove(getStringSearchKey(searchKey));
    }

    private String getStringSearchKey(Object searchKey) {
        return (String) searchKey;
    }
}
