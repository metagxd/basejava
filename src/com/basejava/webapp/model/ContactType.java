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

    private final String type;

    ContactType(String type) {
        this.type = type;
    }

    @Override
    public String toString() {
        return type;
    }
}
