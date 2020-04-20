package com.basejava.webapp.storage;

import com.basejava.webapp.storage.SerializationStrategy.ObjectSerialization;

public class PathStorageTest extends AbstractStorageTest {

    public PathStorageTest() {
        super(new PathStorage(STORAGE_DIRECTORY.toString(),new ObjectSerialization()));
    }
}