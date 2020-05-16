package com.basejava.webapp.storage;

import com.basejava.webapp.Config;
import com.basejava.webapp.ResumeTestData;
import com.basejava.webapp.exception.ExistStorageException;
import com.basejava.webapp.exception.NotExistStorageException;
import com.basejava.webapp.model.Resume;
import org.junit.Before;
import org.junit.Test;

import java.io.File;
import java.util.Arrays;
import java.util.List;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertThat;


public abstract class AbstractStorageTest {
    protected static final Resume resume1 = (ResumeTestData.getResume("uuid1", "name1"));
    protected static final Resume resume2 = (ResumeTestData.getResume("uuid2", "name2"));
    protected static final Resume resume3 = (ResumeTestData.getResume("uuid3", "name3"));
    protected static final Resume resume4 = (ResumeTestData.getResume("uuid4", "name4"));
    protected static final File STORAGE_DIRECTORY = Config.getInstance().getStorageDir();
    protected Storage storage;
    protected static final String dbUrl = Config.getInstance().getDbUrl();
    protected static final String dbUser = Config.getInstance().getDbUser();
    protected static final String dbPassword = Config.getInstance().getDbPassword();

    public AbstractStorageTest(Storage storage) {
        this.storage = storage;
    }

    @Before
    public void setUp() {
        storage.clear();
        storage.save(resume1);
        storage.save(resume3);
        storage.save(resume2);
    }

    @Test
    public void size() {
        assertEquals(3, storage.size());
    }

    @Test
    public void clear() {
        storage.clear();
        assertEquals(0, storage.size());
    }

    @Test
    public void save() {
        storage.save(resume4);
        assertEquals(4, storage.size());
        assertGet(storage.get("uuid4"));
    }

    @Test(expected = ExistStorageException.class)
    public void saveExist() {
        storage.save(resume1);
    }

    @Test
    public void update() {
        Resume resume1 = new Resume("uuid1", "name4");
        storage.update(resume1);
        assertEquals(resume1, storage.get("uuid1"));
    }

    @Test(expected = NotExistStorageException.class)
    public void updateNotExist() {
        storage.update(resume4);
    }

    @Test
    public void get() {
        assertGet(resume1);
        assertGet(resume3);
        assertGet(resume2);
    }

    @Test(expected = NotExistStorageException.class)
    public void getNotExist() {
        storage.get("dummy");
    }

    @Test(expected = NotExistStorageException.class)
    public void delete() {
        storage.delete("uuid3");
        assertEquals(2, storage.size());
        storage.get("uuid3");
    }

    @Test(expected = NotExistStorageException.class)
    public void deleteNotExist() {
        storage.delete("dummy");
    }

    @Test
    public void getAllSorted() {
        List<Resume> actualResumes = storage.getAllSorted();
        List<Resume> expectedResumes = Arrays.asList(resume1, resume2, resume3);
        assertThat(actualResumes, is(expectedResumes));
    }


    private void assertGet(Resume resume) {
        assertEquals(resume, storage.get(resume.getUuid()));
    }
}