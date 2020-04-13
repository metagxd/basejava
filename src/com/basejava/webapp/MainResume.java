package com.basejava.webapp;

import com.basejava.webapp.model.ContactType;
import com.basejava.webapp.model.Resume;

public class MainResume {
    public static void main(String[] args) {
        Resume resume = new Resume("Artyom");
        resume.addContact(ContactType.PHONE,"8800553535");
        resume.addContact(ContactType.MAIL,"metagxd@gmail.com");

        System.out.println(resume.toString());
    }
}
