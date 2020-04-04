package com.basejava.webapp.storage;

import com.basejava.webapp.model.Resume;

import java.util.*;

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

    @Override
    public List<Resume> getAllSorted() {
        Collection<Resume> resumes = map.values();
        ArrayList<Resume> resumeArrayList = new ArrayList<>(resumes);
        Collections.sort(resumeArrayList);
        return resumeArrayList;
    }

    protected String getResumeSearchKey(String uuid) {
        return uuid;
    }

    protected boolean isExist(Object searchKey) {
        return map.containsKey((String) searchKey);
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
