package com.basejava.webapp.model;

import java.util.HashMap;
import java.util.Map;

public class OrganizationSection extends Section {
    private final Map<String, Organization> organizations = new HashMap<>();

    public void addOrganization(Organization organization) {
        if (organizations.containsKey(organization.getTitle())) {
            organizations.get(organization.getTitle()).addPeriods(organization.getPeriods());
            return;
        }
        this.organizations.put(organization.getTitle(), organization);
    }

    @Override
    public String toString() {
        return "OrganizationSection{" +
                "organizations=" + organizations +
                '}';
    }
}
