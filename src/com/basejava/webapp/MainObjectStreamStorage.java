package com.basejava.webapp;

import com.basejava.webapp.model.Resume;
import com.basejava.webapp.storage.AbstractStorage;
import com.basejava.webapp.storage.ObjectStreamStorage;

import java.io.File;

public class MainObjectStreamStorage {
    public static void main(String[] args) {
        AbstractStorage<File> storage = new ObjectStreamStorage(new File("C:\\basejava\\storage"));

        Resume resume1 = ResumeTestData.getResume("uuid1", "name1");
        storage.clear();
        storage.save(resume1);
        Resume resume2 = storage.get("uuid1");

        System.out.println(resume1 + "\n" + resume2);

        System.out.println(resume1.equals(storage.get("uuid1")));
    }
}
