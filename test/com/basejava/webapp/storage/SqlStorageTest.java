package com.basejava.webapp.storage;

public class SqlStorageTest extends AbstractStorageTest {
    public SqlStorageTest() {
        super(new SqlStorage(dbUrl, dbUser, dbPassword));
    }
}
