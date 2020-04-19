package com.basejava.webapp.storage;

import com.basejava.webapp.exception.StorageException;
import com.basejava.webapp.model.Resume;

import java.io.*;

public class ObjectStreamPathStorage extends AbstractPathStorage{
    public ObjectStreamPathStorage(String directory) {
        super(directory);
    }

    @Override
    protected void doWrite(Resume resume, OutputStream outputStream) throws IOException {
        try (ObjectOutputStream objectOutputStream = new ObjectOutputStream(outputStream)) {
            objectOutputStream.writeObject(resume);
        }
    }

    @Override
    protected Resume doRead(InputStream inputStream) throws IOException{
        try(ObjectInputStream objectInputStream = new ObjectInputStream(inputStream)) {
            return (Resume) objectInputStream.readObject();
        } catch (ClassNotFoundException e) {
            throw new StorageException("Class not found error.", e);
        }
    }
}
