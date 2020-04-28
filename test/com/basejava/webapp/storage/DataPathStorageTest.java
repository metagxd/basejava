package com.basejava.webapp.storage;

import com.basejava.webapp.storage.SerializationStrategy.DataStreamSerialization;

public class DataPathStorageTest extends AbstractStorageTest {

    public DataPathStorageTest() {
        super(new PathStorage(STORAGE_DIRECTORY.toString(), new DataStreamSerialization()));
    }
}