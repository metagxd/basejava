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

    //return -1 if resume doesn't exist, return 1 if resume already exist.
    protected int getResumeIndex(String uuid) {
        if (map.containsKey(uuid)) {
            return 1;
        }
        return -1;
    }

    protected void doSave(Resume resume) {
        map.put(resume.getUuid(), resume);
    }

    protected void doUpdate(Resume resume) {
        doSave(resume);
    }

    protected Resume doGet(String uuid) {
        return map.get(uuid);
    }

    protected void doDelete(String uuid) {
        map.remove(uuid);
    }
}
