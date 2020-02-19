package com.basejava.webapp;

import com.basejava.webapp.model.Resume;
import com.basejava.webapp.storage.SortedArrayStorage;

/**
 * Test for your ArrayStorage implementation
 */
public class MainTestSortedArrayStorage {
    private static final SortedArrayStorage SORTED_ARRAY_STORAGE = new SortedArrayStorage();

    public static void main(String[] args) {
        Resume r1 = new Resume();
        r1.setUuid("uuid1");
        Resume r2 = new Resume();
        r2.setUuid("uuid2");
        Resume r3 = new Resume();
        r3.setUuid("uuid3");
        Resume r4 = new Resume();
        r4.setUuid("uuid4");

        SORTED_ARRAY_STORAGE.save(r1);
        SORTED_ARRAY_STORAGE.save(r3);
        SORTED_ARRAY_STORAGE.save(r4);
        System.out.println(SORTED_ARRAY_STORAGE.getResumeIndex(r2.getUuid()));
        System.out.println("Get r2: " + SORTED_ARRAY_STORAGE.get(r2.getUuid()));
        System.out.println("Size: " + SORTED_ARRAY_STORAGE.size());
        
        System.out.println("Get dummy: " + SORTED_ARRAY_STORAGE.get("dummy"));
        printAll();
        System.out.println("Update r1: ");
        SORTED_ARRAY_STORAGE.update(r1);
        SORTED_ARRAY_STORAGE.delete(r2.getUuid());
        printAll();
        System.out.println("Size: " + SORTED_ARRAY_STORAGE.size());
        System.out.println("Update r2: ");
        SORTED_ARRAY_STORAGE.update(r2);
        SORTED_ARRAY_STORAGE.clear();
        printAll();
        System.out.println("Size: " + SORTED_ARRAY_STORAGE.size());
    }

    static void printAll() {
        System.out.println("\nGet All");
        for (Resume r : SORTED_ARRAY_STORAGE.getAll()) {
            System.out.println(r);
        }
    }
}
