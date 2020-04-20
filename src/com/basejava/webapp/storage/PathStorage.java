package com.basejava.webapp.storage;

import com.basejava.webapp.exception.StorageException;
import com.basejava.webapp.model.Resume;
import com.basejava.webapp.storage.SerializationStrategy.SerializationStrategy;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class PathStorage extends AbstractStorage<Path> {
    private final Path directory;
    private final SerializationStrategy serializationStrategy;

    PathStorage(String directory, SerializationStrategy serializationStrategy) {
        this.directory = Paths.get(directory);
        this.serializationStrategy = serializationStrategy;
        if (!Files.isDirectory(this.directory) || !Files.isWritable(this.directory)) {
            throw new StorageException("This directory not writable or not directory!", "");
        }
    }

    @Override
    public int size() {
        return (int) getStreamOfPath().count();
    }

    @Override
    public void clear() {
        getStreamOfPath().forEach(this::doDelete);
    }

    @Override
    protected List<Resume> getListOfResumes() {
        return getStreamOfPath().map(this::doGet).collect(Collectors.toList());
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
            serializationStrategy.doWrite(resume, new BufferedOutputStream(Files.newOutputStream(file)));
        } catch (IOException e) {
            throw new StorageException("Cant write file: " + file.toAbsolutePath().toString(), resume.getUuid(), e);
        }
    }

    @Override
    protected Resume doGet(Path file) {
        try {
            return serializationStrategy.doRead(new BufferedInputStream(Files.newInputStream(file)));
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

    private Stream<Path> getStreamOfPath() {
        try (Stream<Path> list = Files.list(directory)) {
            return list;
        } catch (IOException e) {
            throw new StorageException("Can't read directory!", e);
        }
    }
}
