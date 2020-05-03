package com.basejava.webapp.model;

import com.basejava.webapp.util.DateUtil;
import com.basejava.webapp.util.LocalDateAdapter;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter;
import java.io.Serializable;
import java.time.LocalDate;
import java.time.Month;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;

@XmlAccessorType(XmlAccessType.FIELD)
public class Organization implements Serializable {
    private static final long serialVersionUID = 1L;
    private final List<Period> periods = new ArrayList<>();
    private String title;
    private Link homePage;

    public Organization() {
    }

    public Organization(String title, Link url, Period... periods) {
        this.homePage = url;
        this.title = title;
        this.periods.addAll(Arrays.asList(periods));
    }

    public Organization(String title, Link url, List<Period> periods) {
        this.homePage = url;
        this.title = title;
        this.periods.addAll(periods);
    }

    public String getTitle() {
        return title;
    }

    public Link getHomePage() {
        return homePage;
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
        return periods.equals(that.periods) &&
                title.equals(that.title) &&
                Objects.equals(homePage, that.homePage);
    }

    @Override
    public int hashCode() {
        return Objects.hash(periods, title, homePage);
    }

    @Override
    public String toString() {
        return "Organization{" +
                "title='" + title + '\'' +
                ", homePage=" + homePage +
                ", periods=" + periods +
                '}';
    }

    @XmlAccessorType(XmlAccessType.FIELD)
    public static class Period implements Serializable {
        private static final long serialVersionUID = 1L;
        @XmlJavaTypeAdapter(LocalDateAdapter.class)
        private LocalDate startTime;
        @XmlJavaTypeAdapter(LocalDateAdapter.class)
        private LocalDate endTime;
        private String position;
        private String description;

        public Period() {
        }

        public Period(int startYear, Month startMonth, int endYear, Month endMonth, String position, String description) {
            this(DateUtil.of(startYear, startMonth), DateUtil.of(endYear, endMonth), position, description);
        }
        public Period(int startYear, Month startMonth, int endYear, Month endMonth, String position) {
            this(DateUtil.of(startYear, startMonth), DateUtil.of(endYear, endMonth), position, "");
        }

        public Period(int startYear, Month startMonth, String position, String description) {
            this(DateUtil.of(startYear, startMonth), DateUtil.NOW, position, description);
        }

        public Period(LocalDate startTime, LocalDate endTime, String position, String description) {
            this.startTime = startTime;
            this.endTime = endTime;
            this.description = description;
            this.position = position;
        }

        public LocalDate getStartTime() {
            return startTime;
        }

        public LocalDate getEndTime() {
            return endTime;
        }

        public String getPosition() {
            return position;
        }

        public String getDescription() {
            return description;
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;
            Period period = (Period) o;
            return startTime.equals(period.startTime) &&
                    endTime.equals(period.endTime) &&
                    position.equals(period.position) &&
                    Objects.equals(description, period.description);
        }

        @Override
        public int hashCode() {
            return Objects.hash(startTime, endTime, position, description);
        }

        @Override
        public String toString() {
            return "Period{" +
                    "startTime=" + startTime +
                    ", endTime=" + endTime +
                    ", position='" + position + '\'' +
                    ", description='" + description + '\'' +
                    '}';
        }
    }
}
