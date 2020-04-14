package com.basejava.webapp.model;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class Organization {
    private final Link homePage;
    private final String title;
    private final List<Period> periods = new ArrayList<>();


    public Organization(String title, String urlName, String url, LocalDate startTime, LocalDate endTime, String description) {
        this.homePage = new Link(urlName, url);
        this.title = title;
        periods.add(new Period(startTime, endTime, description));
    }

    public String getTitle() {
        return title;
    }

    public void addPeriods(List<Period> periods) {
        this.periods.addAll(periods);
    }

    public List<Period> getPeriods() {
        return periods;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Organization that = (Organization) o;
        return Objects.equals(homePage, that.homePage) &&
                title.equals(that.title) &&
                Objects.equals(periods, that.periods);
    }

    @Override
    public int hashCode() {
        return Objects.hash(homePage, title, periods);
    }

    @Override
    public String toString() {
        return "Organization{" +
                "homePage=" + homePage +
                ", title='" + title + '\'' +
                ", periods=" + periods +
                '}';
    }

    public static class Period {
        private final String description;
        private final LocalDate startTime;
        private final LocalDate endTime;

        private Period(LocalDate startTime, LocalDate endTime, String description) {
            this.startTime = startTime;
            this.endTime = endTime;
            this.description = description;
        }

        @Override
        public String toString() {
            return startTime.toString() + endTime.toString() + description;
        }
    }
}
