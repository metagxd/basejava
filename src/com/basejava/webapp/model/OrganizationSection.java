package com.basejava.webapp.model;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

public class OrganizationSection extends Section {
    private static final long serialVersionUID = 1L;

    private final Map<String, Organization> organizations = new HashMap<>();

    public OrganizationSection() {
    }

    public OrganizationSection(Organization organization) {
        this.organizations.put(organization.getTitle(), organization);
    }

    public void addOrganization(Organization organization) {
        if (organizations.containsKey(organization.getTitle())) {
            organizations.get(organization.getTitle()).addPeriods(organization.getPeriods());
            return;
        }
        this.organizations.put(organization.getTitle(), organization);
    }

    public void addListOfOrganization(List<Organization> organizationList) {
        for (Organization organization : organizationList) {
            this.addOrganization(organization);
        }
    }

    public Map<String, Organization> getOrganizations() {
        return organizations;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        OrganizationSection that = (OrganizationSection) o;
        return organizations.equals(that.organizations);
    }

    @Override
    public int hashCode() {
        return Objects.hash(organizations);
    }

    @Override
    public String toString() {
        return "OrganizationSection{" +
                "organizations=" + organizations +
                '}';
    }

    @Override
    public String toHtml() {
        StringBuilder stringBuilder = new StringBuilder();
        for (Map.Entry<String, Organization> entry : organizations.entrySet()) {
            stringBuilder
                    .append("<br>")
                    .append(entry.getKey())
                    .append("<br>")
                    .append("<a href =\"//")
                    .append(entry.getValue().getHomePage().getUrl())
                    .append("\">")
                    .append(entry.getValue().getHomePage().getUrl())
                    .append("</a>");
            entry.getValue().getPeriods().forEach(period -> {
                stringBuilder
                        .append("<br>")
                        .append("From ")
                        .append(period.getStartTime().toString())
                        .append(" to ")
                        .append(period.getEndTime().toString())
                        .append("<br>")
                        .append(period.getPosition())
                        .append(" ")
                        .append(period.getDescription());
            });
        }
        return stringBuilder.toString();
    }
}
