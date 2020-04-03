package com.basejava.webapp.storage;

import com.basejava.webapp.model.Resume;
import org.junit.Test;

import java.util.Arrays;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

public class MapStorageTest extends AbstractStorageTest {
    public MapStorageTest() {
        super(new MapStorage());
    }

    @Override
    @Test
    public void getAll() {
        List<Resume> resumes = Arrays.asList(storage.getAll());
        List<Resume> expectedResumes = Arrays.asList(resume1, resume2, resume3);
        assertEquals(3, storage.size());
        assertTrue(resumes.containsAll(expectedResumes));
    }
}
