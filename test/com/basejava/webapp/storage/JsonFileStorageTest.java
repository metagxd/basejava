package com.basejava.webapp.storage;

import com.basejava.webapp.storage.SerializationStrategy.JsonStreamSerialization;

public class JsonFileStorageTest extends AbstractStorageTest {

    public JsonFileStorageTest() {
        super(new FileStorage(STORAGE_DIRECTORY, new JsonStreamSerialization()));
    }
}