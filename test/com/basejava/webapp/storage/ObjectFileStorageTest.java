package com.basejava.webapp.storage;

import com.basejava.webapp.storage.SerializationStrategy.ObjectStreamSerialization;

public class ObjectFileStorageTest extends AbstractStorageTest {

    public ObjectFileStorageTest() {
        super(new FileStorage(STORAGE_DIRECTORY,new ObjectStreamSerialization()));
    }
}