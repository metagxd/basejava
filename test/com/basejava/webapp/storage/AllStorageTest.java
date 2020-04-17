package com.basejava.webapp.storage;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;

@RunWith(Suite.class)
@Suite.SuiteClasses({
        ObjectStreamStorageTest.class,
        ArrayStorageTest.class,
        SortedArrayStorageTest.class,
        ListStorageTest.class,
        UuidMapStorageTest.class,
        ResumeMapStorageTest.class,
})
public class AllStorageTest {
}
