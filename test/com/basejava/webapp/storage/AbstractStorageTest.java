package com.basejava.webapp.storage;

import com.basejava.webapp.exception.ExistStorageException;
import com.basejava.webapp.exception.NotExistStorageException;
import com.basejava.webapp.model.Resume;
import org.junit.Before;
import org.junit.Test;

import java.util.List;

import static org.junit.Assert.assertArrayEquals;
import static org.junit.Assert.assertEquals;


public abstract class AbstractStorageTest {

    public static final String uuid1 = "UUID1";
    public static final String uuid2 = "UUID2";
    public static final String uuid3 = "UUID3";
    public static final String uuid4 = "UUID4";
    public static final Resume resume1 = new Resume(uuid1);
    public static final Resume resume2 = new Resume(uuid2);
    public static final Resume resume3 = new Resume(uuid3);
    public static final Resume resume4 = new Resume(uuid4);
    protected Storage storage;

    public AbstractStorageTest(Storage storage) {
        this.storage = storage;
    }

    @Before
    public void setUp() {
        storage.clear();
        storage.save(resume1);
        storage.save(resume2);
        storage.save(resume3);
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
        assertGet(storage.get(uuid4));
    }

    @Test(expected = ExistStorageException.class)
    public void saveExist() {
        storage.save(resume1);
    }

    @Test
    public void update() {
        Resume resume1 = new Resume(uuid1);
        storage.update(resume1);
        assertEquals(resume1, storage.get(uuid1));
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
        storage.delete(uuid3);
        assertEquals(2, storage.size());
        storage.get(uuid3);
    }

    @Test(expected = NotExistStorageException.class)
    public void deleteNotExist() {
        storage.delete("dummy");
    }

    @Test
    public void getAllSorted() {
        storage.clear();
        storage.save(resume3);
        storage.save(resume1);
        storage.save(resume4);
        storage.save(resume2);

        List<Resume> resumes = storage.getAllSorted();
        Resume[] expectedResumes = {resume1, resume2, resume3, resume4};
        assertArrayEquals(expectedResumes, resumes.toArray());
    }

    private void assertGet(Resume resume) {
        assertEquals(resume, storage.get(resume.getUuid()));
    }
}