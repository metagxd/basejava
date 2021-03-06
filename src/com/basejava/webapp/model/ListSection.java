package com.basejava.webapp.model;

import java.util.List;
import java.util.Objects;

public class ListSection extends Section {
    private static final long serialVersionUID = 1L;
    private List<String> items;

    public ListSection() {
    }

    public List<String> getItems() {
        return items;
    }

    public ListSection(List<String> items) {
        this.items = items;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ListSection that = (ListSection) o;
        return Objects.equals(items, that.items);
    }

    @Override
    public int hashCode() {
        return Objects.hash(items);
    }

    @Override
    public String toString() {
        return String.join("\n", items);
    }

    @Override
    public String toHtml() {
        StringBuilder stringBuilder = new StringBuilder();
        for (String item : items) {
            stringBuilder.append("<li>").append(item).append("</li>");
        }
        return stringBuilder.toString();
    }
}
