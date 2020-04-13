package com.basejava.webapp.model;

import java.time.LocalDate;
import java.util.Objects;

public class Organization {
    private Link homePage;
    private final LocalDate startTime;
    private final LocalDate endTime;
    private String title;
    private String description;

    public Organization(String urlName, String url, LocalDate startTime, LocalDate endTime, String title, String description) {
        this.homePage = new Link (urlName, url);
        this.startTime = startTime;
        this.endTime = endTime;
        this.title = title;
        this.description = description;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Organization that = (Organization) o;
        return homePage.equals(that.homePage) &&
                startTime.equals(that.startTime) &&
                Objects.equals(endTime, that.endTime) &&
                title.equals(that.title) &&
                description.equals(that.description);
    }

    @Override
    public String toString() {
        return "Organization{" +
                "homePage=" + homePage +
                ", startTime=" + startTime +
                ", endTime=" + endTime +
                ", title='" + title + '\'' +
                ", description='" + description + '\'' +
                '}';
    }

    @Override
    public int hashCode() {
        return Objects.hash(homePage, startTime, endTime, title, description);
    }
}
