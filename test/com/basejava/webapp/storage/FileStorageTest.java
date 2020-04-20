package com.basejava.webapp.storage;

import com.basejava.webapp.storage.SerializationStrategy.ObjectSerialization;

public class FileStorageTest extends AbstractStorageTest {

    public FileStorageTest() {
        super(new FileStorage(STORAGE_DIRECTORY,new ObjectSerialization()));
    }
}