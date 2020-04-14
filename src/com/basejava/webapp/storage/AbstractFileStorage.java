package com.basejava.webapp.storage;

import com.basejava.webapp.exception.StorageException;
import com.basejava.webapp.model.Resume;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public abstract class AbstractFileStorage extends AbstractStorage<File> {
    private final File directory;

    AbstractFileStorage(File directory) {
        this.directory = directory;
    }

    @Override
    public int size() {
        if (directory.list() != null && directory.exists()) {
            return directory.list().length;
        }
        return 0;
    }

    @Override
    public void clear() {
        File[] files = directory.listFiles();
        if (files != null) {
            for (File file : files) {
                if (!file.delete()) {
                    throw new StorageException("Can't delete file: ", file.getName());
                }
            }
        }
    }

    @Override
    protected List<Resume> getListOfResumes() {
        List<Resume> resumes = new ArrayList<>();
        for (File file : directory.listFiles()) {
            resumes.add(doRead(file));
        }
        return resumes;
    }

    @Override
    protected File getResumeSearchKey(String uuid) {
        return new File(directory, uuid);
    }

    @Override
    protected boolean isExist(File file) {
        return file.exists();
    }

    @Override
    protected void doSave(Resume resume, File file) {
        try {
            if (!file.createNewFile()) {
                throw new StorageException("Can't create file: ", file.getName());
            }
            doWrite(resume, file);
        } catch (IOException e) {
            throw new StorageException("IO Error", file.getName(), e);
        }

    }

    @Override
    protected void doUpdate(Resume resume, File file) {
        doWrite(resume, file);
    }

    @Override
    protected Resume doGet(File file) {
        return doRead(file);
    }

    @Override
    protected void doDelete(File file) {
        for (File dirFiles : directory.listFiles()) {
            if (dirFiles.getName().equals(file.getName())) {
                if (!dirFiles.delete()) {
                    throw new StorageException("Can't delete file: ", file.getName());
                }
            }
        }
    }

    protected abstract void doWrite(Resume resume, File file);

    protected abstract Resume doRead(File file);
}
