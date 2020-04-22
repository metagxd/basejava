package com.basejava.webapp.model;

public enum ContactType {
    PHONE("Тел."),
    MOBILE("Мобильный."),
    SKYPE("Skype"),
    LINKEDIN("Профиль LinkedIn"),
    MAIL("Почта"),
    GITHUB("Профиль GitHub"),
    STACKOVERFLOW("Профиль Stackoverflow"),
    HOME_PAGE("Домашняя страница");

    private final String title;

    ContactType(String title) {
        this.title = title;
    }

    public String getTitle() {
        return title;
    }
}
