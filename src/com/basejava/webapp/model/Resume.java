package com.basejava.webapp.model;

import java.util.EnumMap;
import java.util.Map;
import java.util.Objects;
import java.util.UUID;

/**
 * Initial resume class
 */
public class Resume implements Comparable<Resume> {

    private final String uuid;
    private final String fullName;
    private final Map<ContactType, Object> contacts = new EnumMap<>(ContactType.class);
    private final Map<SectionType, Object> sections = new EnumMap<>(SectionType.class);

    public Resume(String fullName) {
        this(UUID.randomUUID().toString(), fullName);
    }

    public Resume(String uuid, String fullName) {
        this.uuid = uuid;
        this.fullName = fullName;
    }

    public void addContact(ContactType contactType, Object contactInformation) {
        Objects.requireNonNull(contactType, "contactType can't be null!");
        this.contacts.put(contactType, contactInformation);
    }

    public void addSection(SectionType sectionType, Object object) {
        Objects.requireNonNull(sectionType, "SectionType can't be null!");
        this.sections.put(sectionType, object);
    }

    public Object getContact(ContactType contactType) {
        Objects.requireNonNull(contactType, "ContactType can't be null!");
        return contacts.get(contactType);
    }

    public Object getSection(SectionType sectionType) {
        Objects.requireNonNull(sectionType, "SectionType can't be null!");
        return sections.get(sectionType);
    }

    public String getFullName() {
        return fullName;
    }

    public String getUuid() {
        return uuid;
    }

    @Override
    public String toString() {
        return fullName + " " + uuid + contacts.toString() + sections.toString();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Resume resume = (Resume) o;
        return uuid.equals(resume.uuid) &&
                fullName.equals(resume.fullName) &&
                Objects.equals(contacts, resume.contacts) &&
                Objects.equals(sections, resume.sections);
    }

    @Override
    public int hashCode() {
        return Objects.hash(uuid, fullName, contacts, sections);
    }

    @Override
    public int compareTo(Resume o) {
        return fullName.equals(o.getFullName()) ? uuid.compareTo(o.uuid) : fullName.compareTo(o.fullName);
    }
}
