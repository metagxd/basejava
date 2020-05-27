package com.basejava.webapp.model;

public enum ContactType {
    PHONE("Phone"),
    MOBILE("Mobile phone"),
    SKYPE("Skype") {
        @Override
        public String toHtml0(String value) {
            return "<a href='skype: " + value + "'>" + value + "</a>";
        }
    },
    LINKEDIN("LinkedIn"),
    MAIL("E-mail") {
        @Override
        public String toHtml0(String value) {
            return "<a href='mailto: " + value + "'>" + value + "</a>";
        }
    },
    GITHUB("GitHub"),
    STACKOVERFLOW("Stackoverflow"),
    HOME_PAGE("Home page");

    private final String title;

    ContactType(String title) {
        this.title = title;
    }

    public String getTitle() {
        return title;
    }

    public String toHtml(String value) {
        return (value == null) ? "" : toHtml0(value);
    }

    protected String toHtml0(String value) {
        return title + ": " + value;
    }
}
