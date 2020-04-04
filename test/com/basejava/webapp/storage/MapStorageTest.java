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
}
