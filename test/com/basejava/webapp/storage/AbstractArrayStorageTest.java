package com.basejava.webapp.storage;

import com.basejava.webapp.exception.ExistStorageException;
import com.basejava.webapp.exception.NotExistStorageException;
import com.basejava.webapp.exception.StorageException;
import com.basejava.webapp.model.Resume;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertArrayEquals;
import static org.junit.Assert.assertEquals;


public abstract class AbstractArrayStorageTest {

    private Storage storage;
    String uuid1 = "UUID1";
    String uuid2 = "UUID2";
    String uuid3 = "UUID3";
    String uuid4 = "UUID4";
    Resume resume1 = new Resume(uuid1);
    Resume resume2 = new Resume(uuid2);
    Resume resume3 = new Resume(uuid3);
    Resume resume4 = new Resume(uuid4);

    public AbstractArrayStorageTest(Storage storage) {
        this.storage = storage;
    }

    @Before
    public void setUp() throws Exception {
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

    @Test (expected = ExistStorageException.class)
    public void saveExist() {
        storage.save(resume1);
    }

    @Test
    public void update() {
        Resume resume1 = new Resume(uuid1);
        storage.update(resume1);
        assertEquals(resume1, storage.get(uuid1));
    }

    @Test (expected = NotExistStorageException.class) 
    public void updateNotExist() {
        storage.update(resume4);
    }

    @Test
    public void get() {
        assertGet(resume1);
        assertGet(resume2);
        assertGet(resume3);
    }

    @Test(expected = NotExistStorageException.class)
    public void getNotExist() {
        storage.get("dummy");
    }

    @Test (expected = NotExistStorageException.class)
    public void delete() {
        storage.delete(uuid3);
        assertEquals(2, storage.size());
        storage.get(uuid3);
    }

    @Test (expected = NotExistStorageException.class)
    public void deleteNotExist() {
        storage.delete("dummy");
    }

    @Test
    public void getAll() {
        Resume[] resumes = storage.getAll();
        Resume[] expectedResumes = {resume1, resume2, resume3};
        assertArrayEquals(expectedResumes,resumes);
    }

    @Test(expected = StorageException.class)
    public void overflow() {
        storage.clear();
        try {
            for (int i = 0; i < AbstractArrayStorage.STORAGE_LIMIT; i++) {
                storage.save(new Resume());
            }
        } catch (Exception e) {
            Assert.fail();
        }
        storage.save(new Resume());
    }

    private void assertGet(Resume resume) {
        assertEquals(resume, storage.get(resume.getUuid()));
    }
}