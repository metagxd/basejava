package com.basejava.webapp.storage;

import com.basejava.webapp.storage.SerializationStrategy.ObjectStreamSerialization;

public class ObjectPathStorageTest extends AbstractStorageTest {

    public ObjectPathStorageTest() {
        super(new PathStorage(STORAGE_DIRECTORY.toString(), new ObjectStreamSerialization()));
    }
}