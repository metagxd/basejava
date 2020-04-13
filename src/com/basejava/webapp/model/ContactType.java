package com.basejava.webapp.model;

public enum ContactType {
    PHONE("Тел."),
    MOBILE("Мобильный."),
    SKYPE("Skype"),
    LINKEDIN("Профиль LinkedIn"),
    MAIL("Почта"),
    GITHUB("Профиль GitHub"),
    HOME_PAGE("Домашняя страница");

    private final String type;

    ContactType(String typeName) {
        this.type = typeName;
    }

    @Override
    public String toString() {
        return type;
    }
}
