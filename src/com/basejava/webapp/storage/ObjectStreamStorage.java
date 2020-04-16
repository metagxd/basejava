package com.basejava.webapp.storage;

import com.basejava.webapp.model.Resume;

import java.io.File;

public class ObjectStreamStorage extends AbstractFileStorage{
    ObjectStreamStorage(File directory) {
        super(directory);
    }

    @Override
    protected void doWrite(Resume resume, File file) {

    }

    @Override
    protected Resume doRead(File file) {
        return null;
    }
}
