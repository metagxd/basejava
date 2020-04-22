package com.basejava.webapp.storage;

import com.basejava.webapp.storage.SerializationStrategy.XmlStreamSerialization;

public class XMLPathStorageTest extends AbstractStorageTest {

    public XMLPathStorageTest() {
        super(new PathStorage(STORAGE_DIRECTORY.toString(), new XmlStreamSerialization()));
    }
}