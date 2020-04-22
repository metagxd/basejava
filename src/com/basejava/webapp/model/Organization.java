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
    private Link homePage;
    private String title;

    public Organization() {
    }

    public Organization(String title, String url, Period... periods) {
        this.homePage = new Link(url);
        this.title = title;
        this.periods.addAll(Arrays.asList(periods));
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
                periods.equals(that.periods);
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

        public Period(int startYear, Month startMonth, String position, String description) {
            this(DateUtil.of(startYear, startMonth), DateUtil.NOW, position, description);
        }

        public Period(LocalDate startTime, LocalDate endTime, String position, String description) {
            this.startTime = startTime;
            this.endTime = endTime;
            this.description = description;
            this.position = position;
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;
            Period period = (Period) o;
            return startTime.equals(period.startTime) &&
                    Objects.equals(endTime, period.endTime) &&
                    position.equals(period.position) &&
                    description.equals(period.description);
        }

        @Override
        public int hashCode() {
            return Objects.hash(startTime, endTime, position, description);
        }

        @Override
        public String toString() {
            return startTime.toString() + endTime.toString() + description;
        }
    }
}
