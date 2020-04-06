package com.basejava.webapp.model;

import java.util.Objects;
import java.util.UUID;

/**
 * Initial resume class
 */
public class Resume implements Comparable<Resume> {

    private final String uuid;
    // Unique identifier
    private String fullName;

    public Resume() {
        this("defaultName", UUID.randomUUID().toString());
    }

    public Resume(String fullName, String uuid) {
        this.fullName = fullName;
        this.uuid = uuid;
    }

    public String getFullName() {
        return fullName;
    }

    public String getUuid() {
        return uuid;
    }

    @Override
    public String toString() {
        return fullName + " " + uuid;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Resume resume = (Resume) o;
        return uuid.equals(resume.uuid);
    }

    @Override
    public int hashCode() {
        return Objects.hash(uuid);
    }

    @Override
    public int compareTo(Resume o) {
        if (fullName.equals(o.getFullName())) {
            return uuid.compareTo(o.uuid);
        }
        return fullName.compareTo(o.fullName);
    }
}
