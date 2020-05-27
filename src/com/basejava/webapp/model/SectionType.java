package com.basejava.webapp.model;

public enum SectionType {
    PERSONAL("Personal information"),
    OBJECTIVE("Objective"),
    ACHIEVEMENT("Achievements"),
    QUALIFICATIONS("Qualification"),
    EXPERIENCE("Work experience"),
    EDUCATION("Education");

    private final String title;

    SectionType(String title) {
        this.title = title;
    }

    public String getTitle() {
        return title;
    }
}



