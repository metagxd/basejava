package com.basejava.webapp.storage;

import com.basejava.webapp.model.Resume;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class ResumeMapStorage extends AbstractStorage {
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
    protected Object getResumeSearchKey(String uuid) {
        return map.get(uuid);
    }

    @Override
    protected boolean isExist(Object searchKey) {
        return searchKey != null;
    }

    @Override
    protected void doSave(Resume resume, Object searchKey) {
        map.put(resume.getUuid(), resume);
    }

    @Override
    protected void doUpdate(Resume resume, Object searchKey) {
        doSave(resume, resume.getUuid());
    }

    @Override
    protected Resume doGet(Object searchKey) {
        return map.get(getUUIDFromSearchKey(searchKey));
    }

    @Override
    protected void doDelete(Object searchKey) {
        map.remove(getUUIDFromSearchKey(searchKey));
    }

    private String getUUIDFromSearchKey(Object searchKey) {
        Resume desiredResume = (Resume) searchKey;
        return desiredResume.getUuid();
    }
}
