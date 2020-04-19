package com.basejava.webapp.storage;

import com.basejava.webapp.exception.StorageException;
import com.basejava.webapp.model.Resume;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public abstract class AbstractPathStorage extends AbstractStorage<Path> {
    private final Path directory;

    AbstractPathStorage(String directory) {
        this.directory = Paths.get(directory);
        if (!Files.isDirectory(this.directory) || !Files.isWritable(this.directory)) {
            throw new StorageException("This directory not writable or not directory!", "");
        }
    }

    @Override
    public int size() {
        try (Stream<Path> list = Files.list(directory)) {
            return (int) list.count();
        } catch (IOException e) {
            throw new StorageException("Can't read directory!", e);
        }
    }

    @Override
    public void clear() {
        try {
            Files.list(directory).forEach(this::doDelete);
        } catch (IOException e) {
            throw new StorageException("Can't delete file!", e);
        }
    }

    @Override
    protected List<Resume> getListOfResumes() {
        try {
            return Files.list(directory).map(this::doGet).collect(Collectors.toList());
        } catch (IOException e) {
            throw new StorageException("Can't read resumes!", e);
        }

    }

    @Override
    protected Path getResumeSearchKey(String uuid) {
        return directory.resolve(uuid);
    }

    @Override
    protected boolean isExist(Path file) {
        return Files.isRegularFile(file);
    }

    @Override
    protected void doSave(Resume resume, Path file) {
        doUpdate(resume, file);
    }

    @Override
    protected void doUpdate(Resume resume, Path file) {
        try {
            doWrite(resume, new BufferedOutputStream(Files.newOutputStream(file)));
        } catch (IOException e) {
            throw new StorageException("Cant write file: " + file.toAbsolutePath().toString(), resume.getUuid(), e);
        }
    }

    @Override
    protected Resume doGet(Path file) {
        try {
            return doRead(new BufferedInputStream(Files.newInputStream(file)));
        } catch (IOException e) {
            throw new StorageException("Cant read file: " + file.toAbsolutePath().toString(), null, e);
        }
    }

    @Override
    protected void doDelete(Path file) {
        try {
            Files.delete(file);
        } catch (IOException e) {
            throw new StorageException("Cant delete file: " + file.toAbsolutePath().toString(), null, e);
        }
    }


    protected abstract void doWrite(Resume resume, OutputStream outputStream) throws IOException;

    protected abstract Resume doRead(InputStream inputStream) throws IOException;
}
