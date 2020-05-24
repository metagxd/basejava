package com.basejava.webapp.util;

import com.basejava.webapp.Config;
import com.basejava.webapp.model.Resume;
import com.basejava.webapp.storage.SqlStorage;
import com.basejava.webapp.storage.Storage;

public class TestSqlStorageUtil {
    public static final Config config = Config.getInstance();
    private final static Storage storage = new SqlStorage(config.getDbUrl(), config.getDbUser(), config.getDbPassword());
    private final static Resume resume1 = ResumeDataUtil.getResume("uuid1", "name1");
    private final static Resume resume2 = ResumeDataUtil.getResume("uuid2", "name2");
    private final static Resume resume3 = ResumeDataUtil.getResume("uuid3", "name3");

    static {
        storage.clear();
        storage.save(resume1);
        storage.save(resume2);
        storage.save(resume3);
    }

    public static Storage getStorage() {
        return storage;
    }
}
