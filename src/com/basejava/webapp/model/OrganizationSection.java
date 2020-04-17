package com.basejava.webapp.model;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

public class OrganizationSection extends Section {
    private static final long serialVersionUID = 1L;

    private final Map<String, Organization> organizations = new HashMap<>();

    public void addOrganization(Organization organization) {
        if (organizations.containsKey(organization.getTitle())) {
            organizations.get(organization.getTitle()).addPeriods(organization.getPeriods());
            return;
        }
        this.organizations.put(organization.getTitle(), organization);
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
}
