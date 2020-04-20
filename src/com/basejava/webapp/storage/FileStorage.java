package com.basejava.webapp.storage;

import com.basejava.webapp.exception.StorageException;
import com.basejava.webapp.model.Resume;
import com.basejava.webapp.storage.SerializationStrategy.SerializationStrategy;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class FileStorage extends AbstractStorage<File> {
    private final File directory;
    private final SerializationStrategy serializationStrategy;

    FileStorage(File directory, SerializationStrategy serializationStrategy) {
        this.directory = directory;
        this.serializationStrategy = serializationStrategy;
    }

    @Override
    public int size() {
        return getListOfFiles().length;
    }

    @Override
    public void clear() {
        for (File file : getListOfFiles()) {
            doDelete(file);
        }
    }

    @Override
    protected List<Resume> getListOfResumes() {
        List<Resume> resumes = new ArrayList<>();
        for (File file : getListOfFiles()) {
            resumes.add(doGet(file));
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
                throw new StorageException("Can't create file: " + file.getAbsolutePath(), file.getName());
            }
            doUpdate(resume, file);
        } catch (IOException e) {
            throw new StorageException("IO Error", file.getName(), e);
        }

    }

    @Override
    protected void doUpdate(Resume resume, File file) {
        try {
            serializationStrategy.doWrite(resume, new BufferedOutputStream(new FileOutputStream(file)));
        } catch (IOException e) {
            throw new StorageException("Cant write file: " + file.getAbsolutePath(), resume.getUuid(), e);
        }
    }

    @Override
    protected Resume doGet(File file) {
        try {
            return serializationStrategy.doRead(new BufferedInputStream(new FileInputStream(file)));
        } catch (IOException e) {
            throw new StorageException("Cant read file: " + file.getAbsolutePath(), file.getName(), e);
        }
    }

    @Override
    protected void doDelete(File file) {
        if (!file.delete()) {
            throw new StorageException("Can't delete file: ", file.getName());
        }
    }

    private File[] getListOfFiles() {
        File[] listFiles = directory.listFiles();
        if (listFiles == null) {
            throw new StorageException("Directory read error", "");
        }
        return listFiles;
    }
}
